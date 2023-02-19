package com.luisma.core.services

import java.time.LocalDate

class TimeHelperService {

    fun nextDay(date: LocalDate): LocalDate {
        return date.plusDays(1)
    }

    fun previousDay(date: LocalDate): LocalDate {
        return date.minusDays(1)
    }

    fun isToday(date: LocalDate): Boolean {
        return date == LocalDate.now()
    }

    fun isYesterday(date: LocalDate): Boolean {
        return date == LocalDate.now().minusDays(1)
    }

    fun isTomorrow(date: LocalDate): Boolean {
        return date == LocalDate.now().plusDays(1)
    }

    fun isDateInCurrentYear(date: LocalDate): Boolean {
        return date.year == LocalDate.now().year
    }

}