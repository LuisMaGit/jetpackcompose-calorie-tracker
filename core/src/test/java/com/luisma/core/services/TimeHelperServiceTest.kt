package com.luisma.core.services

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class TimeHelperServiceTest {

    @Test
    fun `nextDay - when it is 31-12-2022 should return 1-1-2023`() {
        val result = TimeHelperService().nextDay(LocalDate.of(2022, 12, 31))
        assertEquals(LocalDate.of(2023, 1, 1), result)
    }

    @Test
    fun `previousDay - when it is 1-1-2023 should return 31-12-2022`() {
        val result = TimeHelperService().previousDay(LocalDate.of(2023, 1, 1))
        assertEquals(LocalDate.of(2022, 12, 31), result)
    }


    @Test
    fun `isToday - check for today`() {
        val result = TimeHelperService().isToday(LocalDate.now())
        assertEquals(result, true)
    }

    @Test
    fun `isYesterday - check for yesterday`() {
        val result = TimeHelperService().isYesterday(LocalDate.now().minusDays(1))
        assertEquals(result, true)
    }

    @Test
    fun `isTomorrow - check for tomorrow`() {
        val result = TimeHelperService().isTomorrow(LocalDate.now().plusDays(1))
        assertEquals(result, true)
    }

    @Test
    fun `isDateInCurrentYear - check for current year`() {
        val result = TimeHelperService().isDateInCurrentYear(LocalDate.now())
        assertEquals(result, true)
    }


}