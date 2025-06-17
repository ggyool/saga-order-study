package com.ggyool.order.application.repository

import com.ggyool.order.application.entity.PurchaseOrder
import com.ggyool.order.common.enums.OrderStatus
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PurchaseOrderRepository : CoroutineCrudRepository<PurchaseOrder, UUID> {

    suspend fun findByOrderIdAndStatus(orderId: UUID, status: OrderStatus): PurchaseOrder?
}