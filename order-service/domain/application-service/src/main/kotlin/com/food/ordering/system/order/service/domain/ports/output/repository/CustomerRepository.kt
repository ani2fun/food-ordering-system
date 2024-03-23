package com.food.ordering.system.order.service.domain.ports.output.repository

import com.food.ordering.system.order.service.domain.core.entity.Customer
import java.util.UUID

interface CustomerRepository {
    fun findCustomer(customerId: UUID): Customer?

    fun save(customer: Customer): Customer
}
