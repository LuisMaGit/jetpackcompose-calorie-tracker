package com.luisma.onboarding_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Layout(
    onClickNext: () -> Unit,
    enabledNextButton: Boolean = true,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
        NextButton(
            modifier = Modifier
                .align(
                    alignment = Alignment.BottomEnd,
                )
                .padding(
                    end = 20.dp,
                    bottom = 20.dp,
                ),
            onClick = onClickNext,
            enabled = enabledNextButton,
        )
    }
}