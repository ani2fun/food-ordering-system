package com.food.ordering.system.common.valueobject

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.math.RoundingMode

class MoneyTest {

    @Test
    fun testEqualityOfValueObjects() {
        // MUST BE EQUAL
        val money1 = Money.of(BigDecimal("10.00"))
        val money2 = Money.of(BigDecimal("10.00"))
        assertEquals(money1, money2)

        // MUST NOT BE EQUAL
        val money3 = Money.of(BigDecimal("10.00"))
        val money4 = Money.of(BigDecimal("5.00"))
        assertNotEquals(money3, money4)

    }

    @Test
    fun testAddition() {
        val money1 = Money.of(BigDecimal("10.25"))
        val money2 = Money.of(BigDecimal("5.75"))

        val sum = money1.add(money2)

        assertEquals(BigDecimal("16.00"), sum.amountAsBigDecimal)
    }

    @Test
    fun testSubtraction() {
        val money1 = Money.of(BigDecimal("10.25"))
        val money2 = Money.of(BigDecimal("5.75"))

        val difference = money1.subtract(money2)

        assertEquals(BigDecimal("4.50"), difference.amountAsBigDecimal)
    }

    @Test
    fun testMultiplication() {
        val money = Money.of(BigDecimal("10.25"))
        val multiplier = 3

        val product = money.multiply(multiplier)

        assertEquals(BigDecimal("30.75"), product.amountAsBigDecimal)
    }

    @Test
    fun testDivision() {
        val money = Money.of(BigDecimal("10.00"))

        // Test division by a non-zero divisor
        val quotient = money.divide(2)
        assertEquals(BigDecimal("5.00"), quotient.amountAsBigDecimal)

        // Test division by zero (should throw an exception)
        assertThrows<IllegalArgumentException> {
            money.divide(0)
        }
    }


    @Test
    fun testGreaterThanZero() {
        val money1 = Money.of(BigDecimal("10.25"))
        val money2 = Money.of(BigDecimal("-5.75"))

        assertTrue(money1.isGreaterThanZero())
        assertFalse(money2.isGreaterThanZero())
    }

    @Test
    fun testIsGreaterThan() {
        val money1 = Money.of(BigDecimal("10.25"))
        val money2 = Money.of(BigDecimal("5.75"))
        val money3 = Money.of(BigDecimal("10.25"))

        // money1 is greater than money2, considering the rounded amounts
        assertTrue(money1.isGreaterThan(money2))

        // money2 is not greater than money1
        assertFalse(money2.isGreaterThan(money1))

        // money1 is not greater than money3, as they have the same rounded amount
        assertFalse(money1.isGreaterThan(money3))
    }


    @Test
    fun testZeroAmount() {
        val zeroMoney = Money.of(BigDecimal.ZERO)
        assertFalse(zeroMoney.isGreaterThanZero())
        assertEquals(BigDecimal("0.00"), zeroMoney.amountAsBigDecimal)
//        the test for zero amounts is failing because the Money class rounds the zero value to "0.00" due to the setScale(2, RoundingMode.HALF_EVEN) in the of factory method. To fix the test, you can modify it to account for this rounding behavior.
//        assertEquals(BigDecimal.ZERO, zeroMoney.amountAsBigDecimal)
    }

    @Test
    fun testNegativeAmount() {
        val negativeMoney = Money.of(BigDecimal("-10.00"))
        assertFalse(negativeMoney.isGreaterThanZero())
        assertEquals(BigDecimal("-10.00"), negativeMoney.amountAsBigDecimal)
    }

    @Test
    fun testSetScale() {
        val money1 = Money.of(BigDecimal("1").divide(BigDecimal("3"), 2, RoundingMode.HALF_EVEN))
        val money2 = Money.of(BigDecimal("7").divide(BigDecimal("10"), 2, RoundingMode.HALF_EVEN))
        assertEquals(BigDecimal("0.33"), money1.amountAsBigDecimal)
        assertEquals(BigDecimal("0.70"), money2.amountAsBigDecimal)
    }

}

