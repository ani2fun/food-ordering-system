package com.food.ordering.system.order.service.domain.dto.track

import com.food.ordering.system.common.valueobject.OrderStatus
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class TrackOrderQuery(
    @NotNull val trackingId: UUID,
)

data class TrackOrderResponse(
    @NotNull val trackingId: UUID,
    @NotNull val orderStatus: OrderStatus,
    @NotNull val failureMessages: List<String>,
)
