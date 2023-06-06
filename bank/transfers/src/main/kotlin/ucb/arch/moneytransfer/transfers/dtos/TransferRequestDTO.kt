package ucb.arch.moneytransfer.transfers.dtos

import java.math.BigDecimal
import java.util.*

data class TransferRequestDTO(
        val transactionId: UUID = UUID.randomUUID(),
        val sourceAccount: String = "",
        val sourceBank: String = "",
        val sourceCurrency: String = "",
        val targetAccountId: String = "",
        val targetBank: String = "",
        val amount: BigDecimal = BigDecimal.ZERO
)
