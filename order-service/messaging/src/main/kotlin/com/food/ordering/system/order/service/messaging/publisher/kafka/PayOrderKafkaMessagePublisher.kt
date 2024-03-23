package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData
import com.food.ordering.system.order.service.domain.core.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.stereotype.Component

@Component
class PayOrderKafkaMessagePublisher(
    val dataMapper: OrderMessagingDataMapper,
    val config: OrderServiceConfigData,
    val kafkaProducer: KafkaProducer<String, RestaurantApprovalRequestAvroModel>,
    val helper: OrderKafkaMessageHelper,
) : OrderPaidRestaurantRequestMessagePublisher {
    companion object {
        val log by LoggerDelegate()
    }

    override fun publish(domainEvent: OrderPaidEvent) {
        val orderId = domainEvent.order.id.asString()
        val restaurantApprovalRequestAvroModel: RestaurantApprovalRequestAvroModel =
            dataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent)

        try {
            kafkaProducer.send(
                config.paymentRequestTopicName,
                orderId,
                restaurantApprovalRequestAvroModel,
                helper.getKafkaCallback(
                    config.paymentResponseTopicName,
                    restaurantApprovalRequestAvroModel,
                    orderId,
                    "RestaurantApprovalRequestAvroModel",
                ),
            )
            log.info(
                "RestaurantApprovalRequestAvroModel sent to Kafka for order id: {}",
                restaurantApprovalRequestAvroModel.orderId,
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
