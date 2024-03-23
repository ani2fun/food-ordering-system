package com.food.ordering.system.kafka.config.data

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class KafkaProducerConfigData(
    @Value("\${kafka-producer-config.key-serializer-class}")
    val keySerializerClass: String,
    @Value("\${kafka-producer-config.value-serializer-class}")
    val valueSerializerClass: String,
    @Value("\${kafka-producer-config.compression-type}")
    val compressionType: String,
    @Value("\${kafka-producer-config.acks}")
    val acks: String,
    @Value("\${kafka-producer-config.batch-size}")
    val batchSize: Int,
    @Value("\${kafka-producer-config.batch-size-boost-factor}")
    val batchSizeBoostFactor: Int,
    @Value("\${kafka-producer-config.linger-ms}")
    val lingerMs: Int,
    @Value("\${kafka-producer-config.request-timeout-ms}")
    val requestTimeoutMs: Int,
    @Value("\${kafka-producer-config.retry-count}")
    val retryCount: Int,
)
