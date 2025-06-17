package com.ggyool.order.common.service

import com.ggyool.order.common.dto.OrderWorkflowActionDto
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface WorkflowActionRetriever {

    fun retrieve(orderId: UUID): Flow<OrderWorkflowActionDto>
}