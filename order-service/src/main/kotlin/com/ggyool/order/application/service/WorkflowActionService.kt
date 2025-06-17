package com.ggyool.order.application.service

import com.ggyool.common.util.validateDuplicateEvent
import com.ggyool.order.application.mapper.EntityDtoMapper
import com.ggyool.order.application.repository.OrderWorkflowActionRepository
import com.ggyool.order.common.dto.OrderWorkflowActionDto
import com.ggyool.order.common.enums.WorkflowAction
import com.ggyool.order.common.service.WorkflowActionRetriever
import com.ggyool.order.common.service.WorkflowActionTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class WorkflowActionService(
    private val orderWorkflowActionRepository: OrderWorkflowActionRepository
) : WorkflowActionTracker, WorkflowActionRetriever {

    override fun retrieve(orderId: UUID): Flow<OrderWorkflowActionDto> {
        return orderWorkflowActionRepository.findByOrderIdOrderByCreatedAt(orderId)
            .map { EntityDtoMapper.toOrderWorkflowActionDto(it) }
    }

    override suspend fun track(orderId: UUID, action: WorkflowAction): Unit =
        validateDuplicateEvent(
            {
                this.orderWorkflowActionRepository.existsByOrderIdAndAction(orderId, action)
            },
            {
                this.orderWorkflowActionRepository.save(
                    EntityDtoMapper.toOrderWorkflowAction(
                        orderId,
                        action
                    )
                )
            }
        )
}