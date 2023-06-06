package ucb.arch.moneytransfer.transfers.repositories

import org.springframework.data.repository.CrudRepository
import ucb.arch.moneytransfer.transfers.constants.TransferStatus
import ucb.arch.moneytransfer.transfers.models.Transfer
import java.util.*

interface TransfersRepository : CrudRepository<Transfer, UUID> {
    fun findByTransactionId(transactionId: UUID): Transfer?
    fun findBySourceAccount(sourceAccount: String): List<Transfer>
    fun findByTargetAccountId(targetAccountId: String): List<Transfer>
}