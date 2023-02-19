package com.luisma.onboarding_ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTPrimaryButton

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick : ()->Unit
) {
    CTPrimaryButton.Filled(
        modifier = modifier,
        onClick = onClick,
        text = stringResource(id = R.string.next),
        elevated = false,
        enabled = enabled,
    )
}