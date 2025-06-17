package com.ggyool.order.messaging.config

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.Response
import com.ggyool.common.messages.inventory.InventoryRequest
import com.ggyool.common.messages.payment.PaymentRequest
import com.ggyool.common.messages.shipping.ShippingRequest
import com.ggyool.common.util.MessageConverter
import com.ggyool.order.messaging.orchestrator.OrderFulfillmentOrchestrator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import java.util.function.Function

@Configuration
class OrderFulfillmentOrchestratorConfig(
    private val orchestrator: OrderFulfillmentOrchestrator
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Bean
    fun orderOrchestrator(): Function<Flow<Message<Response>>, Flow<Message<Request>>> {
        return Function { flow ->
            val mainFlow = flow
                .map { MessageConverter.toRecord(it) }
                .onEach { r -> log.info("order service received {}", r.message) }
                .flatMapConcat { r ->
                    orchestrator.orchestrate(r.message)
                        .onCompletion { r.acknowledgement.acknowledge() }
                }
            val initialFlow = orchestrator.orderInitialRequests()
            merge(mainFlow, initialFlow)
                .map { toMessage(it) }
        }
    }


    private fun toMessage(request: Request): Message<Request> {
        return MessageBuilder.withPayload(request)
            .setHeader(KafkaHeaders.KEY, request.orderId.toString())
            .setHeader(DESTINATION_HEADER, getDestination(request))
            .build()
    }

    private fun getDestination(request: Request): String = when (request) {
        is PaymentRequest -> PAYMENT_REQUEST_CHANNEL
        is InventoryRequest -> INVENTORY_REQUEST_CHANNEL
        is ShippingRequest -> SHIPPING_REQUEST_CHANNEL
        else -> throw IllegalStateException("Unexpected value: $request")
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)

        private const val DESTINATION_HEADER: String = "spring.cloud.stream.sendto.destination"
        private const val PAYMENT_REQUEST_CHANNEL: String = "payment-request-channel"
        private const val INVENTORY_REQUEST_CHANNEL: String = "inventory-request-channel"
        private const val SHIPPING_REQUEST_CHANNEL: String = "shipping-request-channel"
    }
}