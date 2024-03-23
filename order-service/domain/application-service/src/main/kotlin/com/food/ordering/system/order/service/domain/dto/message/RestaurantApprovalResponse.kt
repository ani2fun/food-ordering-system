package com.food.ordering.system.order.service.domain.dto.message

import com.food.ordering.system.common.valueobject.OrderApprovalStatus
import java.time.Instant

data class RestaurantApprovalResponse(
    var id: String? = null,
    var sagaId: String? = null,
    var orderId: String? = null,
    var restaurantId: String? = null,
    var createdAt: Instant? = null,
    var orderApprovalStatus: OrderApprovalStatus? = null,
    var failureMessages: List<String>? = null,
)
