package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval

import com.food.ordering.system.common.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.core.event.OrderPaidEvent

interface OrderPaidRestaurantRequestMessagePublisher : DomainEventPublisher<OrderPaidEvent>
