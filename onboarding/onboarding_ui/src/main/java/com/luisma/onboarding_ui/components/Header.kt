package com.luisma.onboarding_ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun Header(@StringRes id: Int) {
    CTText.H1(
        stringResource(id = id),
        modifier = Modifier.padding(bottom = 16.dp),
        textStyle = CTrackerTheme.typography.h1.copy(
            textAlign = TextAlign.Center
        ),
    )
}