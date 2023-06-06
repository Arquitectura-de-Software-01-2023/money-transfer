package ucb.arch.moneytransfer.transfers.controllers

import feign.FeignException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ucb.arch.moneytransfer.transfers.constants.TransferStatus
import ucb.arch.moneytransfer.transfers.dtos.AccountsPutRequest
import ucb.arch.moneytransfer.transfers.dtos.TransferPostRequest
import ucb.arch.moneytransfer.transfers.dtos.TransferRequestDTO
import ucb.arch.moneytransfer.transfers.models.Transfer
import ucb.arch.moneytransfer.transfers.services.AccountsClient
import ucb.arch.moneytransfer.transfers.services.TransfersService
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/transfers")
class TransferController @Autowired constructor(
    private val transfersService: TransfersService,
    private val accountsClient: AccountsClient
) {
    private var client : String = "maofloresp@gmail.com"

    @PostMapping
    fun createTransfer(@RequestBody body: TransferPostRequest) : ResponseEntity<Transfer> {
        val transfer = Transfer(
            transactionId = UUID.randomUUID(),
            sourceAccount = body.sourceAccount,
            targetAccountId = body.targetAccount,
            amount = body.amount,
            transferStatus = TransferStatus.PROCESSING,
            startedAt = Date(),
            finishedAt = null,
        )

        try {
            val account = accountsClient.getAccount(transfer.sourceAccount) ?: return ResponseEntity.notFound().build()

            if (account.balance - transfer.amount < BigDecimal.ZERO) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build()
            }

            accountsClient.updateAccount(transfer.sourceAccount, AccountsPutRequest(-transfer.amount))

            val createdTransfer = transfersService.createTransfer(transfer)

            val transferToQueue = TransferRequestDTO(
                transactionId = createdTransfer.transactionId,
                sourceAccount = account.number,
                sourceBank = "BEC",
                sourceCurrency = account.currency,
                targetAccountId = createdTransfer.targetAccountId,
                targetBank = "BEC",
                amount = createdTransfer.amount
            )

            val result = transfersService.startTransfer(transferToQueue)

            if (!result) {
                transfersService.updateTransfer(transfer.transactionId, TransferStatus.FAILED)
                return ResponseEntity.internalServerError().build()
            }

            return ResponseEntity.ok(transfer)
        }
        catch (e: FeignException) {
            return ResponseEntity.status(e.status()).build()
        }
    }

    @GetMapping("/{accountId}")
    fun getTransfers(@PathVariable accountId: String) : ResponseEntity<List<Transfer>> {
        val transfers = transfersService.getTransfersBySourceAccount(accountId)

        if (transfers.isEmpty()) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.status(HttpStatus.OK).body(transfers)
    }
}