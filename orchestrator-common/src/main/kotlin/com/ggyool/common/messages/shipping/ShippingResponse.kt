package com.ggyool.common.messages.shipping

import com.ggyool.common.messages.Response
import java.time.Instant
import java.util.UUID

sealed interface ShippingResponse : Response {

    data class Scheduled(
        override val orderId: UUID,
        val shipmentId: UUID,
        val deliveryDate: Instant
    ) : ShippingResponse

    data class Declined(
        override val orderId: UUID,
        val message: String
    ) : ShippingResponse

}