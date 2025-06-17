package com.ggyool.order.application.repository

import com.ggyool.order.application.entity.OrderWorkflowAction
import com.ggyool.order.common.enums.WorkflowAction
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderWorkflowActionRepository : CoroutineCrudRepository<OrderWorkflowAction, UUID> {

    suspend fun existsByOrderIdAndAction(orderId: UUID, action: WorkflowAction): Boolean

    fun findByOrderIdOrderByCreatedAt(orderId: UUID): Flow<OrderWorkflowAction>
}