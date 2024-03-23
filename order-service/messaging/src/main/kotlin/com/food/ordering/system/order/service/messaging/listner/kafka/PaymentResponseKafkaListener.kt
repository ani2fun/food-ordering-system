package com.food.ordering.system.order.service.messaging.listner.kafka

import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class PaymentResponseKafkaListener(
    val paymentResponseMessageListener: PaymentResponseMessageListener,
    val orderMessagingDataMapper: OrderMessagingDataMapper,
) : KafkaConsumer<PaymentResponseAvroModel> {
    companion object {
        val log by LoggerDelegate()
    }

    @KafkaListener(
        id = "\${kafka-consumer-config.payment-consumer-group-id}",
        topics = ["\${order-service.payment-response-topic-name}"],
    )
    override fun receive(
        @Payload messages: List<PaymentResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<Long>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>,
    ) {
        log.info(
            "{} number of payment responses received with keys:{}, partitions:{} and offsets: {}",
            messages.size,
            keys.toString(),
            partitions.toString(),
            offsets.toString(),
        )

        messages.forEach(
            Consumer<PaymentResponseAvroModel> { paymentResponseAvroModel: PaymentResponseAvroModel ->
                if (PaymentStatus.COMPLETED == paymentResponseAvroModel.paymentStatus) {
                    log.info(
                        "Processing successful payment for order id: {}",
                        paymentResponseAvroModel.orderId,
                    )
                    paymentResponseMessageListener.paymentCompleted(
                        orderMessagingDataMapper
                            .paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel),
                    )
                } else if (PaymentStatus.CANCELLED == paymentResponseAvroModel.paymentStatus ||
                    PaymentStatus.FAILED == paymentResponseAvroModel.paymentStatus
                ) {
                    log.info(
                        "Processing unsuccessful payment for order id: {}",
                        paymentResponseAvroModel.orderId,
                    )
                    paymentResponseMessageListener.paymentCancelled(
                        orderMessagingDataMapper
                            .paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel),
                    )
                }
            },
        )
    }
}
