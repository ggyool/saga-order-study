package com.ggyool.common.orchestrator

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.Response
import kotlinx.coroutines.flow.Flow

interface WorkflowOrchestrator {

    fun orchestrate(response: Response): Flow<Request>
}