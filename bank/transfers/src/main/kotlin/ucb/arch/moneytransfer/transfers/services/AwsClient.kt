package ucb.arch.moneytransfer.transfers.services

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ucb.arch.moneytransfer.transfers.dtos.GeneratePDFRequest

@FeignClient(name = "aws", url = "https://xvj0bvceg5.execute-api.us-east-1.amazonaws.com/Prod/")
interface AwsClient {
    @RequestMapping(method = [RequestMethod.POST])
    fun generatePDF(@RequestBody body:GeneratePDFRequest): String
}