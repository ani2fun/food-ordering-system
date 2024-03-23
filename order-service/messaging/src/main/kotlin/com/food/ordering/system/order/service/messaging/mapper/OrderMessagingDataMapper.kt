package com.food.ordering.system.order.service.messaging.mapper

import com.food.ordering.system.common.valueobject.OrderApprovalStatus
import com.food.ordering.system.common.valueobject.PaymentStatus
import com.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel
import com.food.ordering.system.kafka.order.avro.model.Product
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.kafka.order.avro.model.RestaurantOrderStatus
import com.food.ordering.system.order.service.domain.core.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.core.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.core.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.UUID

@Component
class OrderMessagingDataMapper {
    companion object {
        val log by LoggerDelegate()
    }

    fun orderCreatedEventToPaymentRequestAvroModel(orderCreatedEvent: OrderCreatedEvent): PaymentRequestAvroModel {
        val order = orderCreatedEvent.order
        log.info("Order created: $order")

        val paymentRequestAvroModelBuilder =
            PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.customerId.toString())
                .setOrderId(order.id.getID().toString())
                .setPrice(order.price?.amountAsBigDecimal)
                .setCreatedAt(Instant.from(orderCreatedEvent.createdAt))
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)

        val paymentRequestAvroModel = paymentRequestAvroModelBuilder.build()

        log.info("Payment request created: $paymentRequestAvroModel")
        return paymentRequestAvroModel
    }

    fun orderCancelledEventToPaymentRequestAvroModel(orderCancelledEvent: OrderCancelledEvent): PaymentRequestAvroModel {
        val order = orderCancelledEvent.order
        log.info("Order cancelled: $order")

        val paymentRequestAvroModelBuilder =
            PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.customerId.toString())
                .setOrderId(order.id.getID().toString())
                .setPrice(order.price?.amountAsBigDecimal)
                .setCreatedAt(Instant.from(orderCancelledEvent.createdAt))
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)

        val paymentRequestAvroModel = paymentRequestAvroModelBuilder.build()

        log.info("Payment request created: $paymentRequestAvroModel")
        return paymentRequestAvroModel
    }

    fun orderPaidEventToRestaurantApprovalRequestAvroModel(orderPaidEvent: OrderPaidEvent): RestaurantApprovalRequestAvroModel {
        val order = orderPaidEvent.order
        log.info("Order paid: $order")
        val restaurantApprovalRequestAvroModel =
            RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(order.id.asString())
                .setRestaurantId(order.restaurantId.toString())
                .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
                .setPrice(order.price?.amountAsBigDecimal)
                .setProducts(
                    order.orderItems.stream().map { item ->
                        item.quantity?.let {
                            Product.newBuilder()
                                .setId(item.product?.id?.asString())
                                .setQuantity(it).build()
                        }
                    }.toList(),
                )
                .setCreatedAt(Instant.from(orderPaidEvent.createdAt)).build()

        log.info("Restaurant approval request created: $restaurantApprovalRequestAvroModel")
        return restaurantApprovalRequestAvroModel
    }

    fun paymentResponseAvroModelToPaymentResponse(avroModel: PaymentResponseAvroModel): PaymentResponse {
        return PaymentResponse(
            id = avroModel.id,
            sagaId = avroModel.sagaId,
            orderId = avroModel.paymentId,
            paymentId = avroModel.customerId,
            customerId = avroModel.orderId,
            price = avroModel.price,
            createdAt = avroModel.createdAt,
            paymentStatus = PaymentStatus.valueOf(avroModel.paymentStatus.toString()),
            failureMessages = avroModel.failureMessages,
        )
    }

    fun approvalResponseAvroModelToApprovalResponse(avroModel: RestaurantApprovalResponseAvroModel): RestaurantApprovalResponse {
        return RestaurantApprovalResponse(
            id = avroModel.id,
            sagaId = avroModel.sagaId,
            orderId = avroModel.orderId,
            restaurantId = avroModel.restaurantId,
            createdAt = avroModel.createdAt,
            orderApprovalStatus = OrderApprovalStatus.valueOf(avroModel.orderApprovalStatus.toString()),
            failureMessages = avroModel.failureMessages,
        )
    }
}
