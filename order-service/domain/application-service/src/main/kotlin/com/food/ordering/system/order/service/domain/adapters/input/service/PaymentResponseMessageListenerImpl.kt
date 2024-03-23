package com.food.ordering.system.order.service.domain.adapters.input.service

import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class PaymentResponseMessageListenerImpl : PaymentResponseMessageListener {
    companion object {
        val log: Logger by LoggerDelegate()
    }

    override fun paymentCompleted(paymentResponse: PaymentResponse) {
        log.info("PaymentResponseMessageListenerImpl => paymentCompleted(paymentResponse) ")
        TODO("Not yet implemented")
    }

    override fun paymentCancelled(paymentResponse: PaymentResponse) {
        log.info("PaymentResponseMessageListenerImpl => paymentCancelled(paymentResponse) ")
        TODO("Not yet implemented")
    }
}
