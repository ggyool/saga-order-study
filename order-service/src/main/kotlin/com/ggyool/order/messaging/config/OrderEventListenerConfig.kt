package com.ggyool.order.messaging.config

import com.ggyool.order.common.service.OrderEventListener
import com.ggyool.order.messaging.publisher.OrderEventListenerImpl
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderEventListenerConfig {

    @Bean
    fun orderEventListener(): OrderEventListener {
        return OrderEventListenerImpl(
            Channel(
                capacity = Channel.UNLIMITED,
                onBufferOverflow = BufferOverflow.SUSPEND
            )
        )
    }
}