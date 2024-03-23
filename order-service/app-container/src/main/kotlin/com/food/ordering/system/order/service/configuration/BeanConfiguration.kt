package com.food.ordering.system.order.service.configuration

import com.food.ordering.system.order.service.domain.core.IOrderDomainService
import com.food.ordering.system.order.service.domain.core.OrderDomainServiceImpl

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {
    @Bean
    fun orderDomainService(): IOrderDomainService {
        return OrderDomainServiceImpl()
    }
}
