package com.ggyool.order.common.service

import com.ggyool.order.common.dto.PurchaseOrderDto

interface OrderEventListener {

    suspend fun emitOrderCreated(dto: PurchaseOrderDto)
}