package com.ggyool.order.common.service

import com.ggyool.order.common.enums.WorkflowAction
import java.util.UUID

interface WorkflowActionTracker {

    suspend fun track(orderId: UUID, action: WorkflowAction)
}