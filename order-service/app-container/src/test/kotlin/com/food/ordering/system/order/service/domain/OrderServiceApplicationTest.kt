package com.food.ordering.system.order.service.domain

import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import

@Import(OrderServiceApplicationTestConfig::class)
class OrderServiceApplicationTest {

    @Test
    fun contextLoads() {
    }
}

