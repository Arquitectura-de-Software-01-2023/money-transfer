package ucb.arch.moneytransfer.transfers.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.util.*

@Entity
data class Transfer(
    @Id
    val transactionId: UUID = UUID.randomUUID(),
    @NotNull
    val sourceAccount: String = "",
    @NotNull
    val targetAccountId: String = "",
    @NotNull
    val amount: BigDecimal = BigDecimal.ZERO,
    @NotNull
    var transferStatus: String = "",
    @NotNull
    val startedAt: Date = Date(),
    var finishedAt: Date? = null,
)