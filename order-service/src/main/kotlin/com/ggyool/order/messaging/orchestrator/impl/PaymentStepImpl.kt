package com.ggyool.order.messaging.orchestrator.impl

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.payment.PaymentResponse
import com.ggyool.common.orchestrator.RequestCompensator
import com.ggyool.common.orchestrator.RequestSender
import com.ggyool.order.common.enums.WorkflowAction
import com.ggyool.order.common.service.OrderFulfillmentService
import com.ggyool.order.common.service.WorkflowActionTracker
import com.ggyool.order.messaging.mapper.MessageDtoMapper
import com.ggyool.order.messaging.orchestrator.PaymentStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentStepImpl(
    private val tracker: WorkflowActionTracker,
    private val service: OrderFulfillmentService,
) : PaymentStep {

    private lateinit var previousStep: RequestCompensator
    private lateinit var nextStep: RequestSender

    override fun compensate(id: UUID): Flow<Request> = flow {
        tracker.track(id, WorkflowAction.PAYMENT_REFUND_INITIATED)
        emit(MessageDtoMapper.toPaymentRefundRequest(id))
        emitAll(previousStep.compensate(id))
    }

    override fun send(id: UUID): Flow<Request> = flow {
        tracker.track(id, WorkflowAction.PAYMENT_REQUEST_INITIATED)
        service.get(id)?.let {
            emit(MessageDtoMapper.toPaymentProcessRequest(it))
        }
    }

    override fun setPreviousStep(previousStep: RequestCompensator) {
        this.previousStep = previousStep
    }

    override fun setNextStep(nextStep: RequestSender) {
        this.nextStep = nextStep
    }

    override fun onSuccess(response: PaymentResponse.Processed): Flow<Request> = flow {
        tracker.track(response.orderId, WorkflowAction.PAYMENT_PROCESSED)
        emitAll(nextStep.send(response.orderId))
    }

    override fun onFailure(response: PaymentResponse.Declined): Flow<Request> = flow {
        tracker.track(response.orderId, WorkflowAction.PAYMENT_DECLINED)
        emitAll(previousStep.compensate(response.orderId))
    }
}