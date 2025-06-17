package com.ggyool.order.common.service

import com.ggyool.order.common.dto.OrderCreateRequest
import com.ggyool.order.common.dto.OrderDetails
import com.ggyool.order.common.dto.PurchaseOrderDto
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OrderService {
    suspend fun placeOrder(request: OrderCreateRequest): PurchaseOrderDto
    fun getAllOrders(): Flow<PurchaseOrderDto>
    suspend fun getOrderDetails(orderId: UUID): OrderDetails
}