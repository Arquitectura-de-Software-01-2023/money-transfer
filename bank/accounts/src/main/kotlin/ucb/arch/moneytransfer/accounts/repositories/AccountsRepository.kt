package ucb.arch.moneytransfer.accounts.repositories

import org.springframework.data.repository.CrudRepository
import ucb.arch.moneytransfer.accounts.models.Account

interface AccountsRepository : CrudRepository<Account, Long> {
    fun findByNumber(number: String): Account?
}