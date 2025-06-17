package com.ggyool.order.messaging.publisher

import com.ggyool.common.publisher.EventPublisher
import com.ggyool.order.common.dto.PurchaseOrderDto
import com.ggyool.order.common.service.OrderEventListener
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.UUID

class OrderEventListenerImpl(
    private val channel: Channel<UUID>
) : OrderEventListener, EventPublisher<UUID> {

    private val flow = channel.receiveAsFlow()

    override fun publish(): Flow<UUID> {
        return this.flow
    }

    override suspend fun emitOrderCreated(dto: PurchaseOrderDto) {
        this.channel.send(dto.orderId)
    }
}
