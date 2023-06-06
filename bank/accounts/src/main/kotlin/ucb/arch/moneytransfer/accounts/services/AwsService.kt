package ucb.arch.moneytransfer.accounts.services

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ucb.arch.moneytransfer.accounts.models.Currency

@FeignClient(name = "aws", url = "https://ejytbzq24h.execute-api.us-east-1.amazonaws.com/Prod/")
interface AwsService {
    @RequestMapping(method = [RequestMethod.GET])
    fun getCurrencies(): String
}