package com.ggyool.common.messages.payment

import com.ggyool.common.messages.Response
import java.util.UUID

sealed interface PaymentResponse : Response {

    data class Processed(
        override val orderId: UUID,
        val paymentId: UUID,
        val customerId: Int,
        val amount: Int
    ) : PaymentResponse

    data class Declined(
        override val orderId: UUID,
        val message: String
    ) : PaymentResponse
}