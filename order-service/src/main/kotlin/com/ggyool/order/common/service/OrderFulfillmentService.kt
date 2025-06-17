package com.ggyool.order.common.service

import com.ggyool.order.common.dto.OrderShipmentSchedule
import com.ggyool.order.common.dto.PurchaseOrderDto
import java.util.UUID

interface OrderFulfillmentService {

    suspend fun get(orderId: UUID): PurchaseOrderDto?

    suspend fun schedule(orderShipmentSchedule: OrderShipmentSchedule): PurchaseOrderDto?

    suspend fun complete(orderId: UUID): PurchaseOrderDto?

    suspend fun cancel(orderId: UUID): PurchaseOrderDto?
}