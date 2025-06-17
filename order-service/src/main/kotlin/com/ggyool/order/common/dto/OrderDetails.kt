package com.ggyool.order.common.dto

data class OrderDetails(
    val order: PurchaseOrderDto,
    val actions: List<OrderWorkflowActionDto>
)