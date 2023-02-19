package com.luisma.core_ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.luisma.core_ui.theme.CTSpace

object CTSpacerV {
    @Composable
    fun Base() {
        Custom(CTSpace.space1)
    }

    @Composable
    fun Base2() {
        Custom(CTSpace.space2)
    }

    @Composable
    fun Base3() {
        Custom(CTSpace.space3)
    }

    @Composable
    fun Base4() {
        Custom(CTSpace.space3)
    }

    @Composable
    fun Custom(value: Dp) {
        Spacer(modifier = Modifier.height(value))
    }
}

object CTSpacerH {
    @Composable
    fun Base() {
        Custom(CTSpace.space1)
    }

    @Composable
    fun Base2() {
        Custom(CTSpace.space2)
    }

    @Composable
    fun Base3() {
        Custom(CTSpace.space3)
    }

    @Composable
    fun Base4() {
        Custom(CTSpace.space4)
    }

    @Composable
    fun Custom(value: Dp) {
        Spacer(modifier = Modifier.width(value))
    }
}