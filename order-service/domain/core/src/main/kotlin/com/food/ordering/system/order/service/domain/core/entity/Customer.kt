package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.entity.BaseEntity
import com.food.ordering.system.common.valueobject.CustomerId

class Customer(id: CustomerId = CustomerId()) : BaseEntity<CustomerId>(id)