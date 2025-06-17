package com.ggyool.common.messages.shipping

import com.ggyool.common.messages.Request
import java.util.UUID

sealed interface ShippingRequest : Request {

    data class Schedule(
        override val orderId: UUID,
        val customerId: Int,
        val productId: Int,
        val quantity: Int
    ) : ShippingRequest
}