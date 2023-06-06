package ucb.arch.moneytransfer.accounts.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ucb.arch.moneytransfer.accounts.dtos.AccountsPostRequest
import ucb.arch.moneytransfer.accounts.dtos.AccountsPutRequest
import ucb.arch.moneytransfer.accounts.models.Account
import ucb.arch.moneytransfer.accounts.services.AccountsService
import java.math.BigDecimal
import java.util.UUID

@RestController
@RequestMapping("/accounts")
class AccountsController @Autowired constructor(private val service: AccountsService) {

    @PostMapping
    fun createAccount(@RequestBody body: AccountsPostRequest) : ResponseEntity<Account> {

        val account = Account()
        account.currency = body.currency
        account.balance = BigDecimal(5000)
        account.number = UUID.randomUUID().toString()

        val createdAccount = service.createAccount(account)

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount)
    }

    @GetMapping
    fun getAccounts() : ResponseEntity<List<Account>> {
        return ResponseEntity.status(HttpStatus.OK).body(accounts)
    }

    @GetMapping("/{number}")
    fun getAccount(@PathVariable number: String) : ResponseEntity<Account> {
        val account = service.getAccount(number)

        if (account != null) {
            return ResponseEntity.status(HttpStatus.OK).body(account)
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @PutMapping("/{number}")
    fun updateAccount(@PathVariable number: String, @RequestBody body: AccountsPutRequest) : ResponseEntity<Account> {
        val account = service.getAccount(number) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        if (account.balance + body.amount < BigDecimal.ZERO) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(account)
        }

        account.balance += body.amount
        val updatedAccount = service.updateAccount(account)

        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount)
    }

    @DeleteMapping("/{number}")
    fun deleteAccount(@PathVariable number: String) : ResponseEntity<Any> {
        val account = service.getAccount(number) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        if (account.balance > BigDecimal.ZERO) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account has balance")
        }

        service.deleteAccount(account.id)
        return ResponseEntity.status(HttpStatus.OK).build()

    }
}