package com.food.ordering.system.order.service.domain.adapters.input.service

import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class RestaurantApprovalResponseMessageListenerImpl : RestaurantApprovalResponseMessageListener {
    companion object {
        val log: Logger by LoggerDelegate()
    }

    override fun orderApproved(restaurantApprovalResponse: RestaurantApprovalResponse) {
        log.info("RestaurantApprovalResponseMessageListenerImpl => orderApproved(restaurantApprovalResponse) ")
        TODO("Not yet implemented")
    }

    override fun orderRejected(restaurantApprovalResponse: RestaurantApprovalResponse) {
        log.info("RestaurantApprovalResponseMessageListenerImpl => orderRejected(restaurantApprovalResponse) ")
        TODO("Not yet implemented")
    }
}
