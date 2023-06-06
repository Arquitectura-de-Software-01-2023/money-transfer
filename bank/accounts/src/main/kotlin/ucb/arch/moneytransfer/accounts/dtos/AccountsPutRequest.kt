package ucb.arch.moneytransfer.accounts.dtos

import java.math.BigDecimal

data class AccountsPutRequest(
    val amount: BigDecimal
)
