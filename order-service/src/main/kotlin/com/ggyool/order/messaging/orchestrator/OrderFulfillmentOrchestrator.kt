package com.ggyool.order.messaging.orchestrator

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.Response
import com.ggyool.common.messages.inventory.InventoryResponse
import com.ggyool.common.messages.payment.PaymentResponse
import com.ggyool.common.messages.shipping.ShippingResponse
import com.ggyool.common.orchestrator.WorkflowOrchestrator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface OrderFulfillmentOrchestrator : WorkflowOrchestrator {

    fun orderInitialRequests(): Flow<Request>

    override fun orchestrate(response: Response): Flow<Request> {
        return when (response) {
            is PaymentResponse -> this.handle(response)
            is InventoryResponse -> this.handle(response)
            is ShippingResponse -> this.handle(response)
            else -> emptyFlow()
        }
    }

    fun handle(response: PaymentResponse): Flow<Request>

    fun handle(response: InventoryResponse): Flow<Request>

    fun handle(response: ShippingResponse): Flow<Request>
}