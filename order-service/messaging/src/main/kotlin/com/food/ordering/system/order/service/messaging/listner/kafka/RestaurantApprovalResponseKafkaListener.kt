package com.food.ordering.system.order.service.messaging.listner.kafka

import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.lang.String
import java.util.function.Consumer
import kotlin.Int
import kotlin.Long

@Component
class RestaurantApprovalResponseKafkaListener(
    val restaurantApprovalResponseMessageListener: RestaurantApprovalResponseMessageListener,
    val orderMessagingDataMapper: OrderMessagingDataMapper,
) : KafkaConsumer<RestaurantApprovalResponseAvroModel> {
    companion object {
        val log by LoggerDelegate()
    }

    @KafkaListener(
        id = "\${kafka-consumer-config.restaurant-approval-consumer-group-id}",
        topics = ["\${order-service.restaurant-approval-response-topic-name}"],
    )
    override fun receive(
        @Payload messages: List<RestaurantApprovalResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<Long>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>,
    ) {
        log.info(
            "{} number of restaurant approval responses received with keys {}, partitions {} and offsets {}",
            messages.size,
            keys.toString(),
            partitions.toString(),
            offsets.toString(),
        )

        messages.forEach(
            Consumer { it: RestaurantApprovalResponseAvroModel ->

                if (OrderApprovalStatus.APPROVED == it.orderApprovalStatus) {
                    log.info(
                        "Processing approved order for order id: {}",
                        it.orderId,
                    )
                    restaurantApprovalResponseMessageListener.orderApproved(
                        orderMessagingDataMapper
                            .approvalResponseAvroModelToApprovalResponse(it),
                    )
                } else if (OrderApprovalStatus.REJECTED == it.orderApprovalStatus) {
                    log.info(
                        "Processing rejected order for order id: {}, with failure messages: {}",
                        it.orderId,
                        String.join(
                            ",",
                            it.failureMessages,
                        ),
                    )
                    restaurantApprovalResponseMessageListener.orderRejected(
                        orderMessagingDataMapper
                            .approvalResponseAvroModelToApprovalResponse(it),
                    )
                }
            },
        )
    }
}
