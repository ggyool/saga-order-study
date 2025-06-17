package com.ggyool.common.messages.inventory

import com.ggyool.common.messages.Request
import java.util.UUID

sealed interface InventoryRequest : Request {

    data class Deduct(
        override val orderId: UUID,
        val productId: Int,
        val quantity: Int
    ) : InventoryRequest

    data class Restore(
        override val orderId: UUID
    ) : InventoryRequest
}
