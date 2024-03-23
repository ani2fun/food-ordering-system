package com.food.ordering.system.common.event;

interface DomainEvent<T> {
    fun fire()
}
