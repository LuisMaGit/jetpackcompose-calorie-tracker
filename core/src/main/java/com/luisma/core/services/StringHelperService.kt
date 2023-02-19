package com.luisma.core.services

object StringHelperService {

    fun toInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun toFloat(value: String): Float {
        return try {
            value.toFloat()
        } catch (e: NumberFormatException) {
            0f
        }
    }

    fun toDouble(value: String): Double {
        return try {
            value.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }
}