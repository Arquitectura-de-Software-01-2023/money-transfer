package ucb.arch.moneytransfer.transfers.models

import java.math.BigDecimal

data class Account(
    var id : Long = 0,
    var currency : String = "",
    var number : String = "",
    var balance : BigDecimal = BigDecimal.ZERO,
    var client : String = ""
)