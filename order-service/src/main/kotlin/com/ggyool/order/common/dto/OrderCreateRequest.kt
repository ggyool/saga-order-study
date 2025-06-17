package com.ggyool.order.common.dto

data class OrderCreateRequest(
    val customerId: Int,
    val productId: Int,
    val quantity: Int,
    val unitPrice: Int,
)
