package com.ggyool.order.application.controller

import com.ggyool.order.common.dto.OrderCreateRequest
import com.ggyool.order.common.dto.OrderDetails
import com.ggyool.order.common.dto.PurchaseOrderDto
import com.ggyool.order.common.service.OrderService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping
    suspend fun placeOrder(
        @RequestBody request: OrderCreateRequest
    ): PurchaseOrderDto {
        return orderService.placeOrder(request)
    }

    @GetMapping("/all")
    fun getAllOrders(): Flow<PurchaseOrderDto> {
        return orderService.getAllOrders()
    }

    @GetMapping("/{orderId}")
    suspend fun getOrderDetails(
        @PathVariable orderId: UUID
    ): OrderDetails {
        return orderService.getOrderDetails(orderId)
    }
}