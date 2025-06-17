package com.ggyool.common.orchestrator

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.Response
import kotlinx.coroutines.flow.Flow

interface ResponseProcessor<T : Response> {

    fun process(response: T): Flow<Request>
}