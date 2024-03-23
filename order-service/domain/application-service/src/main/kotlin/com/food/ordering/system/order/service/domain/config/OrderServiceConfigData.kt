package com.food.ordering.system.order.service.domain.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class OrderServiceConfigData(
    @Value("\${order-service.payment-request-topic-name}")
    val paymentRequestTopicName: String,
    @Value("\${order-service.payment-response-topic-name}")
    val paymentResponseTopicName: String,
    @Value("\${order-service.restaurant-approval-request-topic-name}")
    val restaurantApprovalRequestTopicName: String,
    @Value("\${order-service.restaurant-approval-response-topic-name}")
    val restaurantApprovalResponseTopicName: String,
)
