package com.ggyool.common.orchestrator

import com.ggyool.common.messages.Request
import kotlinx.coroutines.flow.Flow
import java.util.UUID

fun interface RequestCompensator {

    fun compensate(id: UUID): Flow<Request>
}