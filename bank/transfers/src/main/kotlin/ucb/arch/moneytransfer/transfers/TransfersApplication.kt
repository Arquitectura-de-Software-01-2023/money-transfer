package ucb.arch.moneytransfer.transfers

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class TransfersApplication {
	@Bean
	fun runner(cf: ConnectionFactory): ApplicationRunner {
		return ApplicationRunner {
			var open = false
			while (!open) {
				try {
					cf.createConnection().close()
					open = true
				} catch (e: Exception) {
					Thread.sleep(5000)
				}
			}
		}
	}
}

fun main(args: Array<String>) {
	runApplication<TransfersApplication>(*args)
}
