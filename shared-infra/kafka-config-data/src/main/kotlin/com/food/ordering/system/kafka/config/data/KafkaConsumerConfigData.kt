package com.food.ordering.system.kafka.config.data

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
// @ConfigurationProperties(prefix = "kafka-consumer-config")
data class KafkaConsumerConfigData(
    @Value("\${kafka-consumer-config.key-deserializer}")
    val keyDeserializer: String,
    @Value("\${kafka-consumer-config.value-deserializer}")
    val valueDeserializer: String,
    @Value("\${kafka-consumer-config.auto-offset-reset}")
    val autoOffsetReset: String,
    @Value("\${kafka-consumer-config.specific-avro-reader-key}")
    val specificAvroReaderKey: String,
    @Value("\${kafka-consumer-config.specific-avro-reader}")
    val specificAvroReader: String,
    @Value("\${kafka-consumer-config.batch-listener}")
    val batchListener: Boolean,
    @Value("\${kafka-consumer-config.auto-startup}")
    val autoStartup: Boolean,
    @Value("\${kafka-consumer-config.concurrency-level}")
    val concurrencyLevel: Int,
    @Value("\${kafka-consumer-config.session-timeout-ms}")
    val sessionTimeoutMs: Int,
    @Value("\${kafka-consumer-config.heartbeat-interval-ms}")
    val heartbeatIntervalMs: Int,
    @Value("\${kafka-consumer-config.max-poll-interval-ms}")
    val maxPollIntervalMs: Int,
    @Value("\${kafka-consumer-config.poll-timeout-ms}")
    val pollTimeoutMs: Long,
    @Value("\${kafka-consumer-config.max-poll-records}")
    val maxPollRecords: Int,
    @Value("\${kafka-consumer-config.max-partition-fetch-bytes-default}")
    val maxPartitionFetchBytesDefault: Int,
    @Value("\${kafka-consumer-config.max-partition-fetch-bytes-boost-factor}")
    val maxPartitionFetchBytesBoostFactor: Int,
)
