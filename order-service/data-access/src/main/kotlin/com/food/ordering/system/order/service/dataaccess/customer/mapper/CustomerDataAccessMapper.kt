package com.food.ordering.system.order.service.dataaccess.customer.mapper


import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity
import com.food.ordering.system.order.service.domain.core.entity.Customer
import org.springframework.stereotype.Component

@Component
class CustomerDataAccessMapper {

    fun customerEntityToCustomer(customerEntity: CustomerEntity): Customer {
        return Customer(
            CustomerId(
                customerEntity.id!!
            )
        )
    }
}
