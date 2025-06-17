package com.ggyool.common.messages.payment

import com.ggyool.common.messages.Request
import java.util.UUID

sealed interface PaymentRequest : Request {


    data class Process(
        override val orderId: UUID,
        val customerId: Int,
        val amount: Int
    ) : PaymentRequest


    data class Refund(
        override val orderId: UUID
    ) : PaymentRequest
}