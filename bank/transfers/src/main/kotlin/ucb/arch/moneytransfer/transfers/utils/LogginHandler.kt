package ucb.arch.moneytransfer.transfers.utils

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LoggingHandler : ObservationHandler<Observation.Context> {

    companion object {
        @JvmStatic
        protected val logger : Logger
                = LoggerFactory.getLogger(this::class.java)
    }

    override fun supportsContext(context: Observation.Context): Boolean {
        return true
    }

    override fun onStart(context: Observation.Context) {
        logger.info("Starting context {} ", context)
    }

    override fun onError(context: Observation.Context) {
        logger.info("Error for context {} ", context)
    }

    override fun onEvent(event: Observation.Event, context: Observation.Context) {
        logger.info("Event for context {} and event [ {} ]", context, event)
    }

    override fun onScopeOpened(context: Observation.Context) {
        logger.info("Scope opened for context {} ", context)
    }

    override fun onScopeClosed(context: Observation.Context) {
        logger.info("Scope closed for context {}", context)
    }

    override fun onStop(context: Observation.Context) {
        logger.info("Stopping context {} ", context)
    }
}