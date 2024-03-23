package com.food.ordering.system.common.entity

// This is just an marker class
abstract class AggregateRoot<T>(override val id: T) : BaseEntity<T>(id) {
    override fun getID(): T = this@AggregateRoot.id
}