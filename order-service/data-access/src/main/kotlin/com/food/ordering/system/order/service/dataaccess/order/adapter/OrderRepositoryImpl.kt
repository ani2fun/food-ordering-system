package com.food.ordering.system.order.service.dataaccess.order.adapter

import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper
import com.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.core.valueobject.TrackingId
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Component

@Component
class OrderRepositoryImpl(
    val orderJpaRepository: OrderJpaRepository,
    val orderDataAccessMapper: OrderDataAccessMapper,
) : OrderRepository {
    companion object {
        val log by LoggerDelegate()
    }

    override fun save(order: Order): Order {
        try {
            val orderToOrderEntity = orderDataAccessMapper.orderToOrderEntity(order)

            val savedOrder = orderJpaRepository.save(orderToOrderEntity)

            return orderDataAccessMapper.orderEntityToOrder(savedOrder)
        } catch (ex: Exception) {
            log.error("Failed to saving order into database due to error: {}", ex.message)
            throw IllegalStateException(ex)
        }
    }

    override fun findById(orderId: OrderId): Order? {
        val orderEntity = orderJpaRepository.findById(orderId.getID())
        return orderEntity.orElseThrow {
            throw OrderNotFoundException("No order found for ID: ${orderId.getID()}", NotFoundException())
        }.let {
            orderDataAccessMapper.orderEntityToOrder(it)
        }
    }

    override fun findByTrackingId(trackingId: TrackingId): Order? {
        val orderEntity = orderJpaRepository.findById(trackingId.id)
        return orderEntity.orElseThrow {
            throw OrderNotFoundException("No order found for ID: ${trackingId.id}", NotFoundException())
        }.let {
            orderDataAccessMapper.orderEntityToOrder(it)
        }
    }
}
