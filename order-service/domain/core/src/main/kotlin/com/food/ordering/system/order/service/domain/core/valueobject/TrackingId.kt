package com.food.ordering.system.order.service.domain.core.valueobject

import com.food.ordering.system.common.valueobject.BaseId
import java.util.UUID

data class TrackingId(override val id: UUID = UUID.randomUUID()) : BaseId<UUID>(id)