package ucb.arch.moneytransfer.transfers.dtos

import java.math.BigDecimal

data class AccountsPutRequest(
    val amount: BigDecimal
)
