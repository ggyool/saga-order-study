package com.ggyool.order.common.dto

import com.ggyool.order.common.enums.OrderStatus
import java.time.Instant
import java.util.UUID

data class PurchaseOrderDto(
    val orderId: UUID,
    val customerId: Int,
    val productId: Int,
    val unitPrice: Int,
    val quantity: Int,
    val amount: Int,
    val status: OrderStatus,
    val deliveryDate: Instant?,
)