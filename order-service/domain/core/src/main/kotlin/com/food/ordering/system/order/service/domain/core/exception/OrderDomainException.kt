package com.food.ordering.system.order.service.domain.core.exception

import com.food.ordering.system.common.exception.DomainException

class OrderDomainException(
    override val message: String?,
    override val cause: Throwable?
) : DomainException(message, cause)

class OrderNotFoundException(
    override val message: String?,
    override val cause: Throwable?
) : DomainException(message, cause)