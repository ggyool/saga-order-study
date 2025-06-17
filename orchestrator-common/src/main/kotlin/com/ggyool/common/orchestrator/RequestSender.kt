package com.ggyool.common.orchestrator

import com.ggyool.common.messages.Request
import kotlinx.coroutines.flow.Flow
import java.util.UUID

fun interface RequestSender {

    fun send(id: UUID): Flow<Request>
}