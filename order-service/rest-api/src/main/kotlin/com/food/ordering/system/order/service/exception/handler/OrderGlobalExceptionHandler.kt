package com.food.ordering.system.order.service.exception.handler

import com.food.ordering.system.order.service.domain.core.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.core.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class OrderGlobalExceptionHandler : GlobalExceptionHandler() {
    companion object {
        val log by LoggerDelegate()
    }

    @ResponseBody
    @ExceptionHandler(value = [OrderDomainException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(orderDomainException: OrderDomainException): ErrorDTO {
        log.error(orderDomainException.message, orderDomainException)
        return ErrorDTO(HttpStatus.BAD_REQUEST.reasonPhrase, orderDomainException.message)
    }

    @ResponseBody
    @ExceptionHandler(value = [OrderNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(orderNotFoundException: OrderNotFoundException): ErrorDTO {
        log.error(orderNotFoundException.message, orderNotFoundException)
        return ErrorDTO(HttpStatus.NOT_FOUND.reasonPhrase, orderNotFoundException.message)
    }
}
