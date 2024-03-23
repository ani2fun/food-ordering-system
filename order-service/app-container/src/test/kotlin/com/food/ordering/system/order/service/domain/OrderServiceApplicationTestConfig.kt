package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.core.IOrderDomainService
import com.food.ordering.system.order.service.domain.core.OrderDomainServiceImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackages = ["com.food.ordering.system"])
class OrderServiceApplicationTestConfig {

    @Bean
    fun orderDomainService(): IOrderDomainService {
        return OrderDomainServiceImpl()
    }
}