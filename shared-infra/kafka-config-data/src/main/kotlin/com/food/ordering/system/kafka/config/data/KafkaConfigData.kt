package com.food.ordering.system.kafka.config.data

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

// @ConfigurationProperties(prefix = "kafka-config")
@Configuration
data class KafkaConfigData(
    @Value("\${kafka-config.bootstrap-servers}")
    val bootstrapServers: String,
    @Value("\${kafka-config.schema-registry-url-key}")
    val schemaRegistryUrlKey: String,
    @Value("\${kafka-config.schema-registry-url}")
    val schemaRegistryUrl: String,
    @Value("\${kafka-config.num-of-partitions}")
    val numOfPartitions: Int,
    @Value("\${kafka-config.replication-factor}")
    val replicationFactor: Int,
)
