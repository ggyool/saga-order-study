package com.ggyool.order.common.dto

import com.ggyool.order.common.enums.WorkflowAction
import java.time.Instant
import java.util.UUID

data class OrderWorkflowActionDto(
    val id: UUID,
    val orderId: UUID,
    val action: WorkflowAction,
    val createdAt: Instant
)