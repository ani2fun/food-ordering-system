package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData
import com.food.ordering.system.order.service.domain.core.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.stereotype.Component

@Component
class CancelOrderKafkaMessagePublisher(
    val mapper: OrderMessagingDataMapper,
    val config: OrderServiceConfigData,
    val kafkaProducer: KafkaProducer<String, PaymentRequestAvroModel>,
    val helper: OrderKafkaMessageHelper,
) : OrderCancelledPaymentRequestMessagePublisher {
    companion object {
        val log by LoggerDelegate()
    }

    override fun publish(domainEvent: OrderCancelledEvent) {
        val orderId = domainEvent.order.id.asString()
        log.info("Received OrderCreatedEvent for order id: {} ", orderId)
        val paymentRequestAvroModel: PaymentRequestAvroModel =
            mapper.orderCancelledEventToPaymentRequestAvroModel(domainEvent)
        try {
            kafkaProducer.send(
                config.paymentRequestTopicName,
                orderId,
                paymentRequestAvroModel,
                helper.getKafkaCallback(
                    config.paymentResponseTopicName,
                    paymentRequestAvroModel,
                    orderId,
                    "PaymentRequestAvroModel",
                ),
            )
            log.info(
                "PaymentRequestAvroModel sent to Kafka for order id: {}",
                paymentRequestAvroModel.orderId,
            )
        } catch (ex: Exception) {
            log.error(
                "Error while sending PaymentRequestAvroModel message to kafka with order id: {}, error: {}",
                orderId,
                ex.message,
            )
        }
    }
}
