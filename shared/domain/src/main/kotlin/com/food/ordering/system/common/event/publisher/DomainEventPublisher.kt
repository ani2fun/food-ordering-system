package com.food.ordering.system.common.event.publisher

import com.food.ordering.system.common.event.DomainEvent

interface DomainEventPublisher<T : DomainEvent<*>> {
    fun publish(domainEvent: T)
}