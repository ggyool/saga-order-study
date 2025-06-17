package com.ggyool.order.messaging.mapper

import com.ggyool.common.messages.inventory.InventoryRequest
import com.ggyool.common.messages.payment.PaymentRequest
import com.ggyool.common.messages.shipping.ShippingRequest
import com.ggyool.common.messages.shipping.ShippingResponse
import com.ggyool.order.common.dto.OrderShipmentSchedule
import com.ggyool.order.common.dto.PurchaseOrderDto
import java.util.UUID

object MessageDtoMapper {

    fun toPaymentProcessRequest(dto: PurchaseOrderDto): PaymentRequest {
        return PaymentRequest.Process(
            orderId = dto.orderId,
            customerId = dto.customerId,
            amount = dto.amount
        )
    }

    fun toPaymentRefundRequest(orderId: UUID): PaymentRequest {
        return PaymentRequest.Refund(orderId)
    }

    fun toInventoryDeductRequest(dto: PurchaseOrderDto): InventoryRequest {
        return InventoryRequest.Deduct(
            orderId = dto.orderId,
            productId = dto.productId,
            quantity = dto.quantity
        )
    }

    fun toInventoryRestoreRequest(orderId: UUID): InventoryRequest {
        return InventoryRequest.Restore(orderId)
    }

    fun toShippingScheduleRequest(dto: PurchaseOrderDto): ShippingRequest {
        return ShippingRequest.Schedule(
            orderId = dto.orderId,
            customerId = dto.customerId,
            productId = dto.productId,
            quantity = dto.quantity
        )
    }

    fun toShipmentSchedule(response: ShippingResponse.Scheduled): OrderShipmentSchedule {
        return OrderShipmentSchedule(
            orderId = response.orderId,
            deliveryDate = response.deliveryDate
        )
    }
}