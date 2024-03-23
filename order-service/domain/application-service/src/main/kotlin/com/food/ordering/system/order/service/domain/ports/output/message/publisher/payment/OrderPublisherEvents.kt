package com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment

import com.food.ordering.system.common.event.publisher.DomainEventPublisher
import com.food.ordering.system.order.service.domain.core.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.core.event.OrderCreatedEvent

interface OrderCreatedPaymentRequestMessagePublisher : DomainEventPublisher<OrderCreatedEvent>

interface OrderCancelledPaymentRequestMessagePublisher : DomainEventPublisher<OrderCancelledEvent>
