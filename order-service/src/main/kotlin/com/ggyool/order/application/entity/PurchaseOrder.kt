package com.ggyool.order.application.entity

import com.ggyool.order.common.enums.OrderStatus
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("purchase_order")
class PurchaseOrder(
    @Id
    var orderId: UUID? = null,
    val customerId: Int,
    val productId: Int,
    val quantity: Int,
    val unitPrice: Int,
    val amount: Int,
    var status: OrderStatus,
    var deliveryDate: Instant? = null,
) : Persistable<UUID> {
    constructor(
        customerId: Int,
        productId: Int,
        quantity: Int,
        unitPrice: Int,
        amount: Int,
        status: OrderStatus,
    ) : this(null, customerId, productId, quantity, unitPrice, amount, status, null)

    override fun getId(): UUID? = orderId

    override fun isNew(): Boolean {
        val isNew = id == null
        this.orderId = if (isNew) UUID.randomUUID() else this.id
        return isNew
    }
}