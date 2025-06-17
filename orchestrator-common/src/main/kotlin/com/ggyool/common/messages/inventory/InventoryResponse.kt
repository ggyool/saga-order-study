package com.ggyool.common.messages.inventory

import com.ggyool.common.messages.Response
import java.util.UUID

sealed interface InventoryResponse : Response {

    data class Deducted(
        override val orderId: UUID,
        val inventoryId: UUID,
        val productId: Int,
        val quantity: Int
    ) : InventoryResponse

    data class Declined(
        override val orderId: UUID,
        val message: String
    ) : InventoryResponse
}