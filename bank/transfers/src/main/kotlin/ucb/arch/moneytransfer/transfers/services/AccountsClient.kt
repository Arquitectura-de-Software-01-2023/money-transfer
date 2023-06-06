package ucb.arch.moneytransfer.transfers.services

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ucb.arch.moneytransfer.transfers.dtos.AccountsPutRequest
import ucb.arch.moneytransfer.transfers.models.Account

@FeignClient(name = "bank-accounts")
interface AccountsClient  {
    @RequestMapping(method = [RequestMethod.GET], value = ["/accounts/{number}"])
    fun getAccount(@PathVariable number: String): Account?

    @RequestMapping(method = [RequestMethod.PUT], value = ["/accounts/{number}"])
    fun updateAccount(@PathVariable number: String, @RequestBody body: AccountsPutRequest)
}