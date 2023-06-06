package ucb.arch.moneytransfer.accounts.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ucb.arch.moneytransfer.accounts.models.Account
import ucb.arch.moneytransfer.accounts.repositories.AccountsRepository

@Service
class AccountsService @Autowired constructor(private val accountsRepository: AccountsRepository) {
    fun getAccounts(client: String) : List<Account> = accountsRepository.findAll().toList().filter { it.client == client }
    fun getAccount(number: String) : Account? = accountsRepository.findByNumber(number)
    fun createAccount(account: Account) : Account = accountsRepository.save(account)
    fun deleteAccount(id: Long) = accountsRepository.deleteById(id)
    fun updateAccount(account: Account) = accountsRepository.save(account)
}