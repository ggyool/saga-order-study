package com.ggyool.order.messaging.orchestrator

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.shipping.ShippingResponse
import com.ggyool.common.orchestrator.WorkflowStep
import kotlinx.coroutines.flow.Flow

interface ShippingStep : WorkflowStep<ShippingResponse> {

    override fun process(response: ShippingResponse): Flow<Request> {
        return when (response) {
            is ShippingResponse.Scheduled -> this.onSuccess(response)
            is ShippingResponse.Declined -> this.onFailure(response)
        }
    }

    fun onSuccess(response: ShippingResponse.Scheduled): Flow<Request>

    fun onFailure(response: ShippingResponse.Declined): Flow<Request>
}