package com.luisma.onboarding_ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luisma.core_ui.components.CTPrimaryButton
import com.luisma.core_ui.components.PrimaryButtonDefaults
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    active: Boolean,
    text: String,
    onClick: () -> Unit
) {
    val colorButtons = CTrackerTheme.colors.darkGreen

    if (active) {
        CTPrimaryButton.Filled(
            modifier = modifier,
            text = text,
            colors = PrimaryButtonDefaults.FilledColors(
                backgroundColor = colorButtons,
            ),
            onClick = onClick
        )
        return
    }

    CTPrimaryButton.Outlined(
        modifier = modifier,
        text = text,
        colors = PrimaryButtonDefaults.OutlinedColors(
            contentColor = colorButtons,
        ),
        border = PrimaryButtonDefaults.OutlinedBorder(
            color = colorButtons
        ),
        onClick = onClick
    )
}