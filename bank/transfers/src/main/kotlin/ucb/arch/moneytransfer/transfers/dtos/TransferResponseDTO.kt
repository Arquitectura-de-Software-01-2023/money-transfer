package ucb.arch.moneytransfer.transfers.dtos

import ucb.arch.moneytransfer.transfers.constants.TransferStatus
import java.math.BigDecimal

data class TransferResponseDTO(
        val account: String = "",
        val amount: BigDecimal = BigDecimal.ZERO,
)
