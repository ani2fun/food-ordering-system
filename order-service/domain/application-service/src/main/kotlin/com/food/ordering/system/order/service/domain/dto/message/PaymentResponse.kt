package com.food.ordering.system.order.service.domain.dto.message

import com.food.ordering.system.common.valueobject.PaymentStatus
import java.math.BigDecimal
import java.time.Instant

data class PaymentResponse(
    var id: String? = null,
    var sagaId: String? = null,
    var orderId: String? = null,
    var paymentId: String? = null,
    var customerId: String? = null,
    var price: BigDecimal? = null,
    var createdAt: Instant? = null,
    var paymentStatus: PaymentStatus? = null,
    var failureMessages: List<String?>? = null,
)
