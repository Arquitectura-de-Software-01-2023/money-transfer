package ucb.arch.moneytransfer.transfers.dtos

import java.math.BigDecimal
import java.util.UUID
import javax.print.attribute.standard.Destination

data class GeneratePDFRequest(
    val transaction: UUID = UUID.randomUUID(),
    val origin: String = "",
    val destination: String = "",
    val amount: BigDecimal = BigDecimal.ZERO,
)
