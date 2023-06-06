package ucb.arch.moneytransfer.transfers.services

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ucb.arch.moneytransfer.transfers.constants.BrokerDefinition
import ucb.arch.moneytransfer.transfers.constants.TransferStatus
import ucb.arch.moneytransfer.transfers.dtos.TransferRequestDTO
import ucb.arch.moneytransfer.transfers.dtos.TransferResponseDTO
import ucb.arch.moneytransfer.transfers.models.Transfer
import ucb.arch.moneytransfer.transfers.repositories.TransfersRepository
import java.util.*

@Service
class TransfersService @Autowired constructor(
    private val template: AmqpTemplate,
    private val repository: TransfersRepository
) {
    fun startTransfer(request: TransferRequestDTO) : Boolean {
        try {
            template.convertAndSend(
                    BrokerDefinition.BROKER_EXCHANGE_TRANSFER,
                    BrokerDefinition.BROKER_ROUTING_KEY_TRANSFER_REQUEST,
                    request
            )
        }
        catch (e: Exception) {
            return false
        }

        return true
    }

    fun rejectTransfer(request: TransferResponseDTO) : Boolean {
        try {
            template.convertAndSend(
                    BrokerDefinition.BROKER_EXCHANGE_TRANSFER,
                    BrokerDefinition.BROKER_ROUTING_KEY_TRANSFER_REJECTED,
                    request
            )
        }
        catch (e: Exception) {
            return false
        }

        return true
    }

    fun getTransfer(transactionId: UUID) = repository.findByTransactionId(transactionId)
    fun getTransfersBySourceAccount(sourceAccount: String) = repository.findBySourceAccount(sourceAccount)
    fun getTransfersByTargetAccountId(targetAccountId: String) = repository.findByTargetAccountId(targetAccountId)
    fun createTransfer(transfer: Transfer) = repository.save(transfer)
    fun updateTransfer(transactionId: UUID, transferStatus: String) {
        val transfer = repository.findByTransactionId(transactionId)

        if (transfer != null) {
            transfer.transferStatus = transferStatus
            transfer.finishedAt = Date()
            repository.save(transfer)
        }
    }
}