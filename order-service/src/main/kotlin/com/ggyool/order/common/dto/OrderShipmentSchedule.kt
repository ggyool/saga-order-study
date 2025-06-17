package com.ggyool.order.common.dto

import java.time.Instant
import java.util.UUID

data class OrderShipmentSchedule(
    val orderId: UUID,
    val deliveryDate: Instant
)