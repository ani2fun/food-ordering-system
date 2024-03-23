package com.food.ordering.system.order.service.domain

import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderStatus
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.adapters.input.service.OrderApplicationServiceImpl
import com.food.ordering.system.order.service.domain.adapters.input.service.helpers.OrderCreateCommandHandler
import com.food.ordering.system.order.service.domain.adapters.input.service.helpers.OrderCreateHelper
import com.food.ordering.system.order.service.domain.adapters.input.service.helpers.OrderTrackCommandHandler
import com.food.ordering.system.order.service.domain.core.IOrderDomainService
import com.food.ordering.system.order.service.domain.core.entity.Customer
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.Product
import com.food.ordering.system.order.service.domain.core.entity.Restaurant
import com.food.ordering.system.order.service.domain.core.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.core.valueobject.TrackingId
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.dto.create.OrderItem
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.UUID

// ALL TESTS ARE HERE ARE SAME AS IN `OrderApplicationServiceTest.kt` FILE.
// JUST HERE IT IS USING SPRINGBOOT TEST INSTEAD OF MOCKITO

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [OrderTestConfiguration::class])
class OrderApplicationServiceSpringBootTest {
    @Autowired
    private lateinit var orderApplicationService: OrderApplicationService

    @Autowired
    private lateinit var orderDomainService: IOrderDomainService

    @Autowired
    private lateinit var orderDataMapper: OrderDataMapper

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var restaurantRepository: RestaurantRepository

    private lateinit var createOrderCommand: CreateOrderCommand
    private lateinit var createOrderCommandWrongPrice: CreateOrderCommand
    private lateinit var createOrderCommandWrongProductPrice: CreateOrderCommand

    private val CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")
    private val RESTAURANT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45")
    private val PRODUCT_ID_1 = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")
    private val PRODUCT_ID_2 = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afa")
    private val PRICE = BigDecimal("200.00")

    @Test
    fun testCreateOrder() {
        assertEquals("", "")
    }

    private val orderItem1 = OrderItem(
        productId = PRODUCT_ID_1,
        quantity = 1,
        price = BigDecimal("50.00"),
        subTotal = BigDecimal("50.00")
    )

    private val orderItem2 = OrderItem(
        productId = PRODUCT_ID_2,
        quantity = 3,
        price = BigDecimal("50.00"),
        subTotal = BigDecimal("150.00")
    )

    private val orderStreet1 = OrderAddress(
        street = "street_1",
        postalCode = "1000AB",
        city = "Paris",
        country = "France"
    )

    @BeforeEach
    fun setUp() {
        customerRepository = mock(CustomerRepository::class.java)
        orderRepository = mock(OrderRepository::class.java)
        restaurantRepository = mock(RestaurantRepository::class.java)

        orderDataMapper = OrderDataMapper()

        orderApplicationService =
            OrderApplicationServiceImpl(
                OrderCreateCommandHandler(
                    OrderCreateHelper(
                        orderDomainService,
                        orderRepository,
                        customerRepository,
                        restaurantRepository,
                        orderDataMapper,
                    ),
                    orderDataMapper,
                ),
                OrderTrackCommandHandler(orderDataMapper, orderRepository),
            )

        createOrderCommand =
            CreateOrderCommand(
                customerId = CUSTOMER_ID,
                restaurantId = RESTAURANT_ID,
                address = orderStreet1,
                price = PRICE,
                items =
                listOf(
                    orderItem1,
                    orderItem2,
                ),
            )

        createOrderCommandWrongPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            address = orderStreet1,
            price = BigDecimal(250.00), // Price suppose to be (50x1 + 50x3) = 200
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID_1,
                    quantity = 1,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("50.00")
                ),
                OrderItem(
                    productId = PRODUCT_ID_2,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00")
                )
            )
        )

        createOrderCommandWrongProductPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            address = orderStreet1,
            price = BigDecimal(210.00),
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID_1,
                    quantity = 1,
                    price = BigDecimal("60.00"),
                    subTotal = BigDecimal("60.00")
                ),
                OrderItem(
                    productId = PRODUCT_ID_2,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00")
                )
            )
        )
    }

    @Test
    fun `test Create Order`() {
        assertEquals("", "")
        val customer = Customer(CustomerId(CUSTOMER_ID))
        val restaurantResponse =
            Restaurant(
                id = RestaurantId(createOrderCommand.restaurantId),
                products =
                listOf(
                    Product(ProductId(PRODUCT_ID_1), "product-1", Money.of(BigDecimal(50.00))),
                    Product(ProductId(PRODUCT_ID_2), "product-2", Money.of(BigDecimal(50.00))),
                ),
                active = true,
            )
        val order: Order = orderDataMapper.createOrderCommandToDomainOrder(createOrderCommand)
        order.updateTrackingId(TrackingId(UUID.randomUUID()))

        `when`(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(customer)

        val orderCommandToRestaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)
        `when`(restaurantRepository.findRestaurantInformation(orderCommandToRestaurant))
            .thenReturn(restaurantResponse)

        `when`(orderRepository.save(any<Order>())).thenReturn(order)

        val createOrderResponse: CreateOrderResponse = orderApplicationService.createOrder(createOrderCommand)
        assertEquals(createOrderResponse.orderStatus, OrderStatus.PENDING)
        assertEquals(createOrderResponse.message, "Order created successfully")
        Assertions.assertNotNull(createOrderResponse.trackingId)
    }

    @Test
    fun `test Create Order With Wrong Product Price`() {
        val customer = Customer(CustomerId(CUSTOMER_ID))
        val restaurantResponse =
            Restaurant(
                id = RestaurantId(createOrderCommand.restaurantId),
                products =
                listOf(
                    Product(ProductId(PRODUCT_ID_1), "product-1", Money.of(BigDecimal(50.00))),
                    Product(ProductId(PRODUCT_ID_2), "product-2", Money.of(BigDecimal(50.00))),
                ),
                active = true,
            )
        val order: Order = orderDataMapper.createOrderCommandToDomainOrder(createOrderCommand)
        order.updateTrackingId(TrackingId(UUID.randomUUID()))

        `when`(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(customer)

        val orderCommandToRestaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)
        `when`(restaurantRepository.findRestaurantInformation(orderCommandToRestaurant))
            .thenReturn(restaurantResponse)

        val orderDomainException =
            Assertions.assertThrows(
                OrderDomainException::class.java,
            ) { orderApplicationService.createOrder(createOrderCommandWrongPrice) }
        assertEquals(
            orderDomainException.message,
            "Total price: 250.00 is not equal to Order items total: 200.00!",
        )
    }

    @Test
    fun `test Create Order With Passive Restaurant`() {
        val customer = Customer(CustomerId(CUSTOMER_ID))
        val restaurantResponse =
            Restaurant(
                id = RestaurantId(createOrderCommand.restaurantId),
                products =
                listOf(
                    Product(ProductId(PRODUCT_ID_1), "product-1", Money(BigDecimal("50.00"))),
                    Product(ProductId(PRODUCT_ID_2), "product-2", Money(BigDecimal("50.00"))),
                ),
                active = false,
            )

        val order: Order = orderDataMapper.createOrderCommandToDomainOrder(createOrderCommand)
        order.updateTrackingId(TrackingId(UUID.randomUUID()))

        `when`(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(customer)

        val orderCommandToRestaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)
        `when`(restaurantRepository.findRestaurantInformation(orderCommandToRestaurant))
            .thenReturn(restaurantResponse)

        `when`(
            restaurantRepository.findRestaurantInformation(
                orderDataMapper.createOrderCommandToRestaurant(
                    createOrderCommand,
                ),
            ),
        )
            .thenReturn(restaurantResponse)

        val orderDomainException =
            Assertions.assertThrows(
                OrderDomainException::class.java,
            ) { orderApplicationService.createOrder(createOrderCommand) }
        assertEquals(
            orderDomainException.message,
            "Restaurant with id RestaurantId(id=$RESTAURANT_ID) is currently not active!",
        )
    }
}
