package ucb.arch.moneytransfer.transfers.services

import feign.FeignException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ucb.arch.moneytransfer.transfers.constants.BrokerDefinition
import ucb.arch.moneytransfer.transfers.constants.TransferStatus
import ucb.arch.moneytransfer.transfers.dtos.AccountsPutRequest
import ucb.arch.moneytransfer.transfers.dtos.GeneratePDFRequest
import ucb.arch.moneytransfer.transfers.dtos.TransferRequestDTO
import ucb.arch.moneytransfer.transfers.dtos.TransferResponseDTO

@Service
class TransferRequestConsumer @Autowired constructor(
    private val transfersService: TransfersService,
    private val accountsClient: AccountsClient,
    private val awsClient: AwsClient
) {

    @RabbitListener(queues = [BrokerDefinition.BROKER_QUEUE_TRANSFER_REQUEST])
    fun receiveMessage(request: TransferRequestDTO) {
        try {
            val account = accountsClient.getAccount(request.targetAccountId)
            accountsClient.updateAccount(request.targetAccountId, AccountsPutRequest(request.amount))
            awsClient.generatePDF(GeneratePDFRequest(request.transactionId, request.sourceAccount, request.targetAccountId, request.amount));
            transfersService.updateTransfer(request.transactionId, TransferStatus.SUCCESS)
        }
        catch (e: FeignException) {
            transfersService.updateTransfer(request.transactionId, TransferStatus.FAILED)
            transfersService.rejectTransfer(TransferResponseDTO(request.sourceAccount, request.amount))
            return
        }
    }

    @RabbitListener(queues = [BrokerDefinition.BROKER_QUEUE_TRANSFER_REJECTED])
    fun rejectedMessage(request: TransferResponseDTO) {
        accountsClient.updateAccount(request.account, AccountsPutRequest(request.amount))
    }
}