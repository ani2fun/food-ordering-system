package com.food.ordering.system.common.valueobject

abstract class BaseId<T>(open val id: T) {
    open fun getID() = this.id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseId<*>) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    open fun asString() = this.getID().toString()
}
