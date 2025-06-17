package com.ggyool.common.util

import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import reactor.kafka.receiver.ReceiverOffset

object MessageConverter {

    fun <T> toRecord(message: Message<T>): Record<T> {
        val payload: T = message.payload
        val key = message.headers.get(
            KafkaHeaders.RECEIVED_KEY,
            String::class.java
        )
        val ack = message.headers.get(
            KafkaHeaders.ACKNOWLEDGMENT,
            ReceiverOffset::class.java
        )!!
        return Record(key, payload, ack)
    }
}