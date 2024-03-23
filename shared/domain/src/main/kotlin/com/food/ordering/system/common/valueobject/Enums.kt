package com.food.ordering.system.common.valueobject;


enum class OrderStatus {
    PENDING,
    PAID,
    APPROVED,
    CANCELLING,
    CANCELLED
}

enum class PaymentOrderStatus {
    PENDING,
    CANCELLED
}

enum class PaymentStatus {
    COMPLETED,
    CANCELLED,
    FAILED
}


enum class OrderApprovalStatus {
    APPROVED,
    REJECTED
}


enum class RestaurantOrderStatus {
    PAID
}
