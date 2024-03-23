package com.food.ordering.system.order.service.dataaccess.restaurant.mapper

import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.order.service.domain.core.entity.Product
import com.food.ordering.system.order.service.domain.core.entity.Restaurant
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.stream.Collectors

@Component
class RestaurantDataAccessMapper {
    fun restaurantToRestaurantProducts(restaurant: Restaurant): List<UUID> {
        return restaurant.products?.stream()?.map {
            it.getID().getID()
        }?.collect(Collectors.toList()) ?: emptyList()
    }

    fun restaurantEntityToRestaurant(restaurantEntities: List<RestaurantEntity>): Restaurant {
        val restaurantEntity: RestaurantEntity = restaurantEntities.stream().findFirst()
            .orElseThrow { IllegalArgumentException("Restaurant could not be found!") }

        val restaurantProducts: List<Product> = restaurantEntities.map {
            Product(
                id = ProductId(it.productId!!),// TODO: SAFE CALL REPLACE WITH MORE FUNCTIONAL APPROACH
                name = it.productName!!, // TODO: SAFE CALL REPLACE WITH MORE FUNCTIONAL APPROACH
                price = Money.of(it.productPrice!!)
            )
        }.toList()

        return Restaurant(
            id = RestaurantId(restaurantEntity.restaurantId!!), // TODO: SAFE CALL REPLACE WITH MORE FUNCTIONAL APPROACH
            products = restaurantProducts,
            active = restaurantEntity.restaurantActive!! // TODO: SAFE CALL REPLACE WITH MORE FUNCTIONAL APPROACH
        )

    }
}
