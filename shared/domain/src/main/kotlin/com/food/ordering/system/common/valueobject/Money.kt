package com.food.ordering.system.common.valueobject;

import java.math.BigDecimal
import java.math.RoundingMode


// This Money object is true value object.😎
// Value objects are immutable and only holds data, so identifier is not important for them. 😇
// That means 2 value objects with same data, but different id’s considered to be the same value object.🤯🤣
data class Money(private val amount: BigDecimal) {
    val amountAsBigDecimal: BigDecimal
        get() = amount

    fun isGreaterThanZero(): Boolean {
        return amount.compareTo(BigDecimal.ZERO) > 0
    }

    fun isGreaterThan(money: Money): Boolean {
        return amount.compareTo(money.amount) > 0
    }

    fun add(money: Money): Money {
        return Money(setScale(this.amount.add(money.amount)))
    }

    fun subtract(money: Money): Money {
        return Money(setScale(this.amount.subtract(money.amount)))
    }

    fun multiply(multiplier: Int): Money {
        return Money(setScale(this.amount.multiply(BigDecimal(multiplier))))
    }

    fun divide(divisor: Int): Money {
        require(divisor != 0) { "Divisor cannot be zero" }
        return Money(setScale(this.amount.divide(BigDecimal(divisor), 2, RoundingMode.HALF_EVEN)))
    }

    // http://www.aligelenler.com/2014/10/using-fractional-numbers-in-java_14.html
    // https://docs.oracle.com/cd/E19957-01/806-3568/ncg_goldberg.html
    // https://www.mindprod.com/jgloss/floatingpoint.html
    fun setScale(input: BigDecimal): BigDecimal {
        return input.setScale(2, RoundingMode.HALF_EVEN)
    }

    companion object {

        val ZERO: Money = of(BigDecimal.ZERO)

        fun of(amount: BigDecimal): Money {
//            Optional: Add validation to ensure non-negativity(fix the -ve values test if you do add following require condition.🤓 ))
//            require(amount >= BigDecimal.ZERO) { "Amount must be non-negative" }
            return Money(amount.setScale(2, RoundingMode.HALF_EVEN))
        }
    }

}