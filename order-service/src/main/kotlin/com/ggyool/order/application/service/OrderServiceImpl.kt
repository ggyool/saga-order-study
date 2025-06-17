package com.ggyool.order.application.service

import com.ggyool.order.application.mapper.EntityDtoMapper
import com.ggyool.order.application.repository.PurchaseOrderRepository
import com.ggyool.order.common.dto.OrderCreateRequest
import com.ggyool.order.common.dto.OrderDetails
import com.ggyool.order.common.dto.PurchaseOrderDto
import com.ggyool.order.common.service.OrderEventListener
import com.ggyool.order.common.service.OrderService
import com.ggyool.order.common.service.WorkflowActionRetriever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderServiceImpl(
    private val purchaseOrderRepository: PurchaseOrderRepository,
    private val orderEventListener: OrderEventListener,
    private val workflowActionRetriever: WorkflowActionRetriever,
) : OrderService {

    override suspend fun placeOrder(request: OrderCreateRequest): PurchaseOrderDto {
        val purchaseOrder = purchaseOrderRepository.save(
            EntityDtoMapper.toPurchaseOrder(request)
        )
        val purchaseOrderDto = EntityDtoMapper.toPurchaseOrderDto(purchaseOrder)
        orderEventListener.emitOrderCreated(purchaseOrderDto)
        return purchaseOrderDto
    }

    override fun getAllOrders(): Flow<PurchaseOrderDto> {
        return purchaseOrderRepository.findAll()
            .map { EntityDtoMapper.toPurchaseOrderDto(it) }
    }

    override suspend fun getOrderDetails(orderId: UUID): OrderDetails {
        val purchaseOrder = purchaseOrderRepository.findById(orderId)
            ?: throw RuntimeException("[$orderId] 해당 주문이 존재하지 않습니다.")
        return EntityDtoMapper.toOrderDetails(
            order = EntityDtoMapper.toPurchaseOrderDto(purchaseOrder),
            actions = workflowActionRetriever.retrieve(orderId).toList()
        )
    }
}