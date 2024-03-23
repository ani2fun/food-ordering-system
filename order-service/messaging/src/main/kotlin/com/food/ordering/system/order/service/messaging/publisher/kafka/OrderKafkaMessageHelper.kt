package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.function.BiConsumer

@Component
class OrderKafkaMessageHelper {
    companion object {
        val log by LoggerDelegate()
    }

    fun <T> getKafkaCallback(
        topicName: String,
        requestAvroModel: T,
        orderId: String,
        requestAvroModelName: String,
    ): BiConsumer<SendResult<String, T>, Throwable?> {
        return BiConsumer<SendResult<String, T>, Throwable?> { result, ex ->
            if (ex != null) {
                log.error("Error while sending message: ${ex.message}")
            } else {
                val metadata = result.recordMetadata
                log.info("Message sent successfully to topic: $topicName")
                log.info(
                    """Received successful response from Kafka for
                        |order id: $orderId, 
                        |Topic: ${metadata.topic()}, 
                        |Partition: ${metadata.partition()}, 
                        |Offset: ${metadata.offset()}, 
                        |Timestamp: ${metadata.timestamp()}
                    """.trimMargin(),
                )
            }
        }
    }
}
