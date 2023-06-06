package ucb.arch.moneytransfer.transfers.dtos

import java.math.BigDecimal
import java.util.*

data class TransferPostRequest(
    val sourceAccount: String,
    val targetAccount: String,
    val targetBank: String,
    val amount: BigDecimal
)
