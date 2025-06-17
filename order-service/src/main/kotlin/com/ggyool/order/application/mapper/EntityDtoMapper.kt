package com.ggyool.order.application.mapper

import com.ggyool.order.application.entity.OrderWorkflowAction
import com.ggyool.order.application.entity.PurchaseOrder
import com.ggyool.order.common.dto.OrderCreateRequest
import com.ggyool.order.common.dto.OrderDetails
import com.ggyool.order.common.dto.OrderWorkflowActionDto
import com.ggyool.order.common.dto.PurchaseOrderDto
import com.ggyool.order.common.enums.OrderStatus
import com.ggyool.order.common.enums.WorkflowAction
import java.time.Instant
import java.util.UUID

object EntityDtoMapper {

    fun toPurchaseOrder(request: OrderCreateRequest): PurchaseOrder {
        return PurchaseOrder(
            customerId = request.customerId,
            productId = request.productId,
            quantity = request.quantity,
            unitPrice = request.unitPrice,
            amount = request.unitPrice * request.quantity,
            status = OrderStatus.PENDING
        )
    }

    fun toPurchaseOrderDto(purchaseOrder: PurchaseOrder): PurchaseOrderDto {
        return PurchaseOrderDto(
            orderId = purchaseOrder.orderId!!,
            customerId = purchaseOrder.customerId,
            productId = purchaseOrder.productId,
            quantity = purchaseOrder.quantity,
            unitPrice = purchaseOrder.unitPrice,
            amount = purchaseOrder.amount,
            status = purchaseOrder.status,
            deliveryDate = purchaseOrder.deliveryDate
        )
    }

    fun toOrderWorkflowAction(orderId: UUID, action: WorkflowAction): OrderWorkflowAction {
        return OrderWorkflowAction(
            orderId = orderId,
            action = action,
            createdAt = Instant.now()
        )
    }

    fun toOrderWorkflowActionDto(orderWorkflowAction: OrderWorkflowAction): OrderWorkflowActionDto {
        return OrderWorkflowActionDto(
            id = orderWorkflowAction.id!!,
            orderId = orderWorkflowAction.orderId,
            action = orderWorkflowAction.action,
            createdAt = orderWorkflowAction.createdAt
        )
    }

    fun toOrderDetails(
        order: PurchaseOrderDto,
        actions: List<OrderWorkflowActionDto>
    ): OrderDetails {
        return OrderDetails(
            order = order,
            actions = actions
        )
    }
}