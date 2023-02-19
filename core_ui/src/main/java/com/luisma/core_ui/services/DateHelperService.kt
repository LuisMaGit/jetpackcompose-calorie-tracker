package com.luisma.core_ui.services

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.luisma.core.services.TimeHelperService
import com.luisma.core_ui.R
import java.time.LocalDate

object TimeHelperUIService {

    private val mapMonthName =
        mapOf(
            1 to R.string.january,
            2 to R.string.february,
            3 to R.string.march,
            4 to R.string.april,
            5 to R.string.may,
            6 to R.string.june,
            7 to R.string.july,
            8 to R.string.august,
            9 to R.string.september,
            10 to R.string.october,
            11 to R.string.november,
            12 to R.string.december,
        )

    private fun monthName(month: Int): Int {
        return mapMonthName[month] ?: 0
    }

    @Composable
    fun dateLabel(date: LocalDate): String {
        val service = TimeHelperService()
        if (service.isToday(date)) {
            return stringResource(id = R.string.today)
        }

        if (service.isYesterday(date)) {
            return stringResource(id = R.string.yesterday)
        }

        if (service.isTomorrow(date)) {
            return stringResource(id = R.string.tomorrow)
        }

        return "${date.dayOfMonth} ${stringResource(id = monthName(date.month.value))} ${date.year}"
    }
}