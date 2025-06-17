package com.ggyool.common.publisher

import kotlinx.coroutines.flow.Flow


interface EventPublisher<T> {

    fun publish(): Flow<T>
}