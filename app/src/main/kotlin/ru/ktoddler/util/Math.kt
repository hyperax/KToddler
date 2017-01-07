package ru.ktoddler.util

import java.math.BigDecimal

fun BigDecimal?.toStr():String {
    return this?.toPlainString() ?: "0";
}

fun String?.toBigDecimal():BigDecimal {

    if (this == null || this == "") return BigDecimal.ZERO

    try {
        return BigDecimal(this.trim({ it <= ' ' }))
    } catch (e: Exception) {
        return BigDecimal.ZERO
    }
}
