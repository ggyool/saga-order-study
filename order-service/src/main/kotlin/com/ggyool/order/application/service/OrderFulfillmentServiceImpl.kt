package com.ggyool.order.application.service

import com.ggyool.order.application.entity.PurchaseOrder
import com.ggyool.order.application.mapper.EntityDtoMapper.toPurchaseOrderDto
import com.ggyool.order.application.repository.PurchaseOrderRepository
import com.ggyool.order.common.dto.OrderShipmentSchedule
import com.ggyool.order.common.dto.PurchaseOrderDto
import com.ggyool.order.common.enums.OrderStatus
import com.ggyool.order.common.service.OrderFulfillmentService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderFulfillmentServiceImpl(
    private val purchaseOrderRepository: PurchaseOrderRepository
) : OrderFulfillmentService {

    override suspend fun get(orderId: UUID): PurchaseOrderDto? {
        return purchaseOrderRepository.findById(orderId)?.let {
            toPurchaseOrderDto(it)
        }
    }

    override suspend fun schedule(orderShipmentSchedule: OrderShipmentSchedule): PurchaseOrderDto? {
        return update(orderShipmentSchedule.orderId) {
            it.deliveryDate = orderShipmentSchedule.deliveryDate
        }
    }

    override suspend fun complete(orderId: UUID): PurchaseOrderDto? {
        return update(orderId) {
            it.status = OrderStatus.COMPLETED
        }
    }

    override suspend fun cancel(orderId: UUID): PurchaseOrderDto? {
        return update(orderId) {
            it.status = OrderStatus.CANCELLED
        }
    }

    private suspend fun update(orderId: UUID, update: (PurchaseOrder) -> Unit): PurchaseOrderDto? {
        return purchaseOrderRepository.findByOrderIdAndStatus(orderId, OrderStatus.PENDING)
            ?.let {
                update(it)
                toPurchaseOrderDto(purchaseOrderRepository.save(it))
            }
    }
}