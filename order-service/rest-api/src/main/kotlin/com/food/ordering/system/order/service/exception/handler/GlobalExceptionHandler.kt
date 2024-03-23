package com.food.ordering.system.order.service.exception.handler

import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.stream.Collectors

@ControllerAdvice
class GlobalExceptionHandler {
    companion object {
        val log by LoggerDelegate()
    }

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception): ErrorDTO {
        log.error(exception.message, exception)
        return ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, exception.message)
    }

    @ResponseBody
    @ExceptionHandler(value = [ValidationException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(validationException: ValidationException): ErrorDTO {
        val errorDTO: ErrorDTO
        if (validationException is ConstraintViolationException) {
            val violations = extractViolationsFromException(validationException)

            log.error(violations, validationException)

            errorDTO = ErrorDTO(HttpStatus.BAD_REQUEST.reasonPhrase, violations)
        } else {
            val exceptionMessage = validationException.message

            log.error(exceptionMessage, validationException)

            errorDTO = ErrorDTO(HttpStatus.BAD_REQUEST.reasonPhrase, exceptionMessage)
        }
        return errorDTO
    }

    private fun extractViolationsFromException(validationException: ConstraintViolationException): String {
        return validationException.constraintViolations
            .stream()
            .map { obj: ConstraintViolation<*> -> obj.message }
            .collect(Collectors.joining("--"))
    }
}
