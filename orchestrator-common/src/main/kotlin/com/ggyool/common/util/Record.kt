package com.ggyool.common.util

import reactor.kafka.receiver.ReceiverOffset

data class Record<T>(
    val key: String?,
    val message: T,
    val acknowledgement: ReceiverOffset
)