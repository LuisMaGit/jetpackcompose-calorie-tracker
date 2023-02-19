package com.luisma.core_ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CTrackerColors(
    val shade1: Color,
    val shade2: Color,
    val shade3: Color,
    val background: Color,
    val inverseBackground: Color,
    val brightGreen: Color,
    val darkGreen: Color,
    val orange: Color,
    val carbColor: Color,
    val proteinColor: Color,
    val fatColor: Color,
    val redError: Color,
) {
    companion object {
        fun light(): CTrackerColors {
            return CTrackerColors(
                shade1 = Color(0XFFEEEEEE),
                shade2 = Color(0XFFE7E7E7),
                shade3 = Color(0XFFDDDDDD),
                background = Color(0XFFFFFFFF),
                inverseBackground = Color(0XF0000000),
                brightGreen = Color(0xFF00C713),
                darkGreen = Color(0xFF00790C),
                orange = Color(0xFFFFAA00),
                carbColor = Color(0xFFEEFF00),
                proteinColor = Color(0xFFFFAA00),
                fatColor = Color(0xFFF44336),
                redError = Color.Red
            )
        }

        fun night(): CTrackerColors {
            return CTrackerColors(
                shade1 = Color(0XFFDDDDDD),
                shade2 = Color(0XFFE7E7E7),
                shade3 = Color(0XFFEEEEEE),
                background = Color(0XF0000000),
                inverseBackground = Color(0XFFFFFFFF),
                brightGreen = Color(0xFF00C713),
                darkGreen = Color(0xFF00790C),
                orange = Color(0xFFFFAA00),
                carbColor = Color(0xFFEEFF00),
                proteinColor = Color(0xFFFFAA00),
                fatColor = Color(0xFFF44336),
                redError = Color.Red
            )
        }
    }
}

internal val LocalCTColors = staticCompositionLocalOf { CTrackerColors.light() }