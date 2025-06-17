package com.ggyool.common.processor

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.Response

interface RequestProcessor<T : Request, R : Response> {

    suspend fun process(request: T): R
}