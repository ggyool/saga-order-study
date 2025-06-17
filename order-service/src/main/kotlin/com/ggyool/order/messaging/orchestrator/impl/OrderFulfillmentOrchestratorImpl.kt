package com.ggyool.order.messaging.orchestrator.impl

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.inventory.InventoryResponse
import com.ggyool.common.messages.payment.PaymentResponse
import com.ggyool.common.messages.shipping.ShippingResponse
import com.ggyool.common.publisher.EventPublisher
import com.ggyool.order.common.service.OrderFulfillmentService
import com.ggyool.order.messaging.orchestrator.InventoryStep
import com.ggyool.order.messaging.orchestrator.OrderFulfillmentOrchestrator
import com.ggyool.order.messaging.orchestrator.PaymentStep
import com.ggyool.order.messaging.orchestrator.ShippingStep
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderFulfillmentOrchestratorImpl(
    private val paymentStep: PaymentStep,
    private val inventoryStep: InventoryStep,
    private val shippingStep: ShippingStep,
    private val service: OrderFulfillmentService,
    private val eventPublisher: EventPublisher<UUID>,
) : OrderFulfillmentOrchestrator {

    private lateinit var workflow: Workflow

    @PostConstruct
    private fun init() {
        this.workflow = Workflow.startWith(paymentStep)
            .thenNext(inventoryStep)
            .thenNext(shippingStep)
            .doOnFailure { id -> this.service.cancel(id) }
            .doOnSuccess { id -> this.service.complete(id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun orderInitialRequests(): Flow<Request> {
        return this.eventPublisher.publish()
            .flatMapConcat { this.workflow.firstStep.send(it) }
    }

    override fun handle(response: PaymentResponse): Flow<Request> = paymentStep.process(response)

    override fun handle(response: InventoryResponse): Flow<Request> =
        inventoryStep.process(response)

    override fun handle(response: ShippingResponse): Flow<Request> = shippingStep.process(response)
}