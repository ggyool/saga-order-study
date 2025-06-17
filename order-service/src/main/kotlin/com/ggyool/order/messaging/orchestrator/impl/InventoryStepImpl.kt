package com.ggyool.order.messaging.orchestrator.impl

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.inventory.InventoryResponse
import com.ggyool.common.orchestrator.RequestCompensator
import com.ggyool.common.orchestrator.RequestSender
import com.ggyool.order.common.enums.WorkflowAction
import com.ggyool.order.common.service.OrderFulfillmentService
import com.ggyool.order.common.service.WorkflowActionTracker
import com.ggyool.order.messaging.mapper.MessageDtoMapper
import com.ggyool.order.messaging.orchestrator.InventoryStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class InventoryStepImpl(
    private val tracker: WorkflowActionTracker,
    private val service: OrderFulfillmentService,
) : InventoryStep {

    private lateinit var previousStep: RequestCompensator
    private lateinit var nextStep: RequestSender

    override fun compensate(id: UUID): Flow<Request> = flow {
        tracker.track(id, WorkflowAction.INVENTORY_RESTORE_INITIATED)
        emit(MessageDtoMapper.toInventoryRestoreRequest(id))
        emitAll(previousStep.compensate(id))
    }

    override fun send(id: UUID): Flow<Request> = flow {
        tracker.track(id, WorkflowAction.INVENTORY_REQUEST_INITIATED)
        service.get(id)?.let {
            emit(MessageDtoMapper.toInventoryDeductRequest(it))
        }
    }

    override fun setPreviousStep(previousStep: RequestCompensator) {
        this.previousStep = previousStep
    }

    override fun setNextStep(nextStep: RequestSender) {
        this.nextStep = nextStep
    }

    override fun onSuccess(response: InventoryResponse.Deducted): Flow<Request> = flow {
        tracker.track(response.orderId, WorkflowAction.INVENTORY_DEDUCTED)
        emitAll(nextStep.send(response.orderId))
    }

    override fun onFailure(response: InventoryResponse.Declined): Flow<Request> = flow {
        tracker.track(response.orderId, WorkflowAction.INVENTORY_DECLINED)
        emitAll(previousStep.compensate(response.orderId))
    }
}