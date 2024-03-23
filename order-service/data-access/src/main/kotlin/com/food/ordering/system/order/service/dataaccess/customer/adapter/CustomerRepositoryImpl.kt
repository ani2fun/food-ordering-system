package com.food.ordering.system.order.service.dataaccess.customer.adapter


import com.food.ordering.system.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper
import com.food.ordering.system.order.service.dataaccess.customer.repository.CustomerJpaRepository
import com.food.ordering.system.order.service.domain.core.entity.Customer
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerRepositoryImpl(
    val customerJpaRepository: CustomerJpaRepository,
    val customerDataAccessMapper: CustomerDataAccessMapper
) : CustomerRepository {

    @Override
    override fun findCustomer(customerId: UUID): Customer? {
        return customerJpaRepository.findById(customerId).map { customerDataAccessMapper.customerEntityToCustomer(it) }
            .getOrNull()
    }

    override fun save(customer: Customer): Customer {
        TODO("Not yet implemented")
    }

}
