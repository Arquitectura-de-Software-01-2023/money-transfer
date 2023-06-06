package ucb.arch.moneytransfer.transfers.configs

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ucb.arch.moneytransfer.transfers.constants.BrokerDefinition

@Configuration
class BrokerConfig {
    @Bean
    fun transferExchange(): DirectExchange {
        return DirectExchange(BrokerDefinition.BROKER_EXCHANGE_TRANSFER)
    }

    @Bean
    fun transferRequestQueue(): Queue {
        return Queue(BrokerDefinition.BROKER_QUEUE_TRANSFER_REQUEST)
    }

    @Bean
    fun transferResponseQueue(): Queue {
        return Queue(BrokerDefinition.BROKER_QUEUE_TRANSFER_RESPONSE)
    }

    @Bean
    fun transferRejectedQueue(): Queue {
        return Queue(BrokerDefinition.BROKER_QUEUE_TRANSFER_REJECTED)
    }

    @Bean
    fun transferRequestBinding(): Binding {
        return BindingBuilder.bind(transferRequestQueue())
                .to(transferExchange()).with(BrokerDefinition.BROKER_ROUTING_KEY_TRANSFER_REQUEST)
    }

    @Bean
    fun transferResponseBinding(): Binding {
        return BindingBuilder.bind(transferResponseQueue())
                .to(transferExchange()).with(BrokerDefinition.BROKER_ROUTING_KEY_TRANSFER_RESPONSE)
    }

    @Bean
    fun transferRejectedBinding(): Binding {
        return BindingBuilder.bind(transferRejectedQueue())
                .to(transferExchange()).with(BrokerDefinition.BROKER_ROUTING_KEY_TRANSFER_REJECTED)
    }

    @Bean
    fun converter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun amqpTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = converter()
        return rabbitTemplate
    }
}