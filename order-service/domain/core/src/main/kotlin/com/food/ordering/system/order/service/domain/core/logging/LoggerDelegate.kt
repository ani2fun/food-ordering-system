package com.food.ordering.system.order.service.domain.core.logging

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggerDelegate<in T : Any>(
    private val loggerName: String? = null
) : ReadOnlyProperty<T, Logger> {
    override fun getValue(thisRef: T, property: KProperty<*>): Logger =
        LoggerFactory.getLogger(loggerName ?: classForLogging(thisRef.javaClass).name)

    private fun classForLogging(javaClass: Class<T>) = javaClass.enclosingClass
        ?.takeIf { it.kotlin.companionObject?.java == javaClass }
        ?: javaClass
}