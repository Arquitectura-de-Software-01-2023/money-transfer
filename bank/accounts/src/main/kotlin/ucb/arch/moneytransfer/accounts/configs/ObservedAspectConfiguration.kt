package ucb.arch.moneytransfer.accounts.configs

import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import ucb.arch.moneytransfer.accounts.utils.LoggingHandler


@Configuration
class ObservedAspectConfiguration {
    @Bean
    @Primary
    fun observedAspect(observationRegistry: ObservationRegistry): ObservedAspect? {
        observationRegistry.observationConfig().observationHandler(LoggingHandler())
        return ObservedAspect(observationRegistry)
    }
}