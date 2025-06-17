package com.ggyool.order.messaging.orchestrator.impl

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.shipping.ShippingResponse
import com.ggyool.common.orchestrator.RequestCompensator
import com.ggyool.common.orchestrator.RequestSender
import com.ggyool.order.common.enums.WorkflowAction
import com.ggyool.order.common.service.OrderFulfillmentService
import com.ggyool.order.common.service.WorkflowActionTracker
import com.ggyool.order.messaging.mapper.MessageDtoMapper
import com.ggyool.order.messaging.orchestrator.ShippingStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ShippingStepImpl(
    private val tracker: WorkflowActionTracker,
    private val service: OrderFulfillmentService,
) : ShippingStep {

    private lateinit var previousStep: RequestCompensator
    private lateinit var nextStep: RequestSender

    override fun compensate(id: UUID): Flow<Request> = flow {
        emitAll(previousStep.compensate(id))
    }

    override fun send(id: UUID): Flow<Request> = flow {
        tracker.track(id, WorkflowAction.SHIPPING_SCHEDULE_INITIATED)
        service.get(id)?.let {
            emit(MessageDtoMapper.toShippingScheduleRequest(it))
        }
    }

    override fun setPreviousStep(previousStep: RequestCompensator) {
        this.previousStep = previousStep
    }

    override fun setNextStep(nextStep: RequestSender) {
        this.nextStep = nextStep
    }

    override fun onSuccess(response: ShippingResponse.Scheduled): Flow<Request> = flow {
        tracker.track(response.orderId, WorkflowAction.SHIPPING_SCHEDULED)

        service.schedule(
            MessageDtoMapper.toShipmentSchedule(response)
        )?.let {
            emitAll(nextStep.send(response.orderId))
        }
    }

    override fun onFailure(response: ShippingResponse.Declined): Flow<Request> = flow {
        tracker.track(response.orderId, WorkflowAction.SHIPPING_DECLINED)
        emitAll(previousStep.compensate(response.orderId))
    }
}