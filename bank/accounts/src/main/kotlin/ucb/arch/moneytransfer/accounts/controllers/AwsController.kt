package ucb.arch.moneytransfer.accounts.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ucb.arch.moneytransfer.accounts.services.AwsService

@RestController
@RequestMapping("/accounts")
class AwsController @Autowired constructor(private val service: AwsService){
    @GetMapping("/currencies")
    fun getCurrencies() : ResponseEntity<String> {
        val currencies = service.getCurrencies()

        return ResponseEntity.status(HttpStatus.OK).body(currencies)
    }
}