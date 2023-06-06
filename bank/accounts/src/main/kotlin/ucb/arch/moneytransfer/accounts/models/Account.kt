package ucb.arch.moneytransfer.accounts.models

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,
    @NotNull
    var currency : String = "",
    @NotNull
    @Column(unique = true)
    var number : String = "",
    @NotNull
    var balance : BigDecimal = BigDecimal.ZERO,
    @NotNull
    var client : String = "",
)
