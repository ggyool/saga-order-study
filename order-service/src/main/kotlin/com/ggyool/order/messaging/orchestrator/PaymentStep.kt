package com.ggyool.order.messaging.orchestrator

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.payment.PaymentResponse
import com.ggyool.common.orchestrator.WorkflowStep
import kotlinx.coroutines.flow.Flow

interface PaymentStep : WorkflowStep<PaymentResponse> {

    override fun process(response: PaymentResponse): Flow<Request> {
        return when (response) {
            is PaymentResponse.Processed -> this.onSuccess(response)
            is PaymentResponse.Declined -> this.onFailure(response)
        }
    }

    fun onSuccess(response: PaymentResponse.Processed): Flow<Request>

    fun onFailure(response: PaymentResponse.Declined): Flow<Request>
}