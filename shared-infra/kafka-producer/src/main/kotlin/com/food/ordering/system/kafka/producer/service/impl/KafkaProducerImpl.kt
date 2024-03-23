package com.food.ordering.system.kafka.producer.service.impl

import com.food.ordering.system.kafka.producer.exception.KafkaProducerException
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import jakarta.annotation.PreDestroy
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.kafka.KafkaException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

@Component
class KafkaProducerImpl<K : Serializable, V : SpecificRecordBase>(
    private val kafkaTemplate: KafkaTemplate<K, V>?,
) : KafkaProducer<K, V> {
    companion object {
        val log by LoggerDelegate()
    }

    @PreDestroy
    fun close() {
        log.info("Closing kafka producer!")
        kafkaTemplate?.destroy()
    }

    override fun send(
        topicName: String,
        key: K,
        message: V,
        callback: BiConsumer<SendResult<K, V>, Throwable?>,
    ) {
        log.info("Sending message={} to topic={}", message, topicName)
        try {
            val kafkaResultFuture: CompletableFuture<SendResult<K, V>> =
                kafkaTemplate?.send(
                    topicName,
                    key,
                    message,
                ) ?: throw KafkaProducerException("Kafka Template is null => $kafkaTemplate.")

            kafkaResultFuture.whenComplete(callback)
        } catch (e: KafkaException) {
            log.error("Error on kafka producer with key: {}, message: {} and exception: {}", key, message, e.message)
            throw KafkaProducerException("Error on kafka producer with key: $key and message: $message")
        }
    }
}
