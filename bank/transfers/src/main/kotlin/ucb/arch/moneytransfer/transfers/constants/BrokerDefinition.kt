package ucb.arch.moneytransfer.transfers.constants

object BrokerDefinition {
    const val BROKER_EXCHANGE_TRANSFER = "TransferExchange"
    const val BROKER_QUEUE_TRANSFER_REQUEST = "TransferRequestQueue"
    const val BROKER_QUEUE_TRANSFER_RESPONSE = "TransferResponseQueue"
    const val BROKER_QUEUE_TRANSFER_REJECTED = "TransferRejectedQueue"
    const val BROKER_ROUTING_KEY_TRANSFER_REQUEST = "TransferRequest"
    const val BROKER_ROUTING_KEY_TRANSFER_RESPONSE = "TransferResponse"
    const val BROKER_ROUTING_KEY_TRANSFER_REJECTED = "TransferRejected"
}