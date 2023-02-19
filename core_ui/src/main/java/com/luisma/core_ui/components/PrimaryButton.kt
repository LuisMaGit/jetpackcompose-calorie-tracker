package com.luisma.core_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.theme.CTSpace
import com.luisma.core_ui.theme.CTrackerTheme
import com.luisma.core_ui.theme.CalorieTrackerTheme

object CTPrimaryButton {
    @Composable
    fun Filled(
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PrimaryButtonDefaults.ContentPadding,
        enabled: Boolean = true,
        onClick: () -> Unit,
        text: String,
        elevated: Boolean = false,
        colors: ButtonColors = PrimaryButtonDefaults.FilledColors(),
    ) {
        val elevation = if (elevated) ButtonDefaults.elevation() else null

        Button(
            modifier = modifier,
            onClick = onClick,
            colors = colors,
            contentPadding = contentPadding,
            shape = PrimaryButtonDefaults.Shape,
            enabled = enabled,
            elevation = elevation,
        ) {
            Text(text = text)
        }
    }

    @Composable
    fun Outlined(
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PrimaryButtonDefaults.ContentPadding,
        enabled: Boolean = true,
        onClick: () -> Unit,
        text: String,
        colors: ButtonColors = PrimaryButtonDefaults.OutlinedColors(),
        border: BorderStroke = PrimaryButtonDefaults.OutlinedBorder(),
        icon: ImageVector? = null,
    ) {
        Button(
            modifier = modifier,
            onClick = onClick,
            colors = colors,
            contentPadding = contentPadding,
            shape = PrimaryButtonDefaults.Shape,
            border = border,
            enabled = enabled
        ) {

            @Composable
            fun textComp() = Text(text = text)

            if (icon == null) {
                textComp()
            } else {
                Row {
                    Icon(
                        tint = colors.contentColor(enabled = enabled).value,
                        modifier = Modifier.padding(end = CTSpace.space1),
                        imageVector = icon,
                        contentDescription = ""
                    )
                    textComp()
                }
            }
        }
    }


    @Composable
    private fun Text(text: String) {
        CTText.H4(
            text = text,
            maxLines = 1,
        )
    }
}


object PrimaryButtonDefaults {

    //Common
    val ContentPadding = PaddingValues(
        horizontal = 20.dp,
        vertical = 18.dp
    )

    val Shape = RoundedCornerShape(40.dp)

    @Composable
    fun ContrastColor() = CTrackerTheme.colors.brightGreen

    @Composable
    fun DisabledColor() = CTrackerTheme.colors.shade3

    //Filled
    @Composable
    fun FilledColors(
        backgroundColor: Color = ContrastColor(),
        disabledColor: Color = DisabledColor(),
        contentColor: Color = CTrackerTheme.colors.background,
        disabledContentColor: Color = CTrackerTheme.colors.shade2,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledBackgroundColor = disabledColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    //Outlined
    @Composable
    fun OutlinedBorder(
        width: Dp = 2.dp,
        color: Color = ContrastColor(),
    ): BorderStroke {
        return BorderStroke(
            width = width,
            color = color
        )
    }

    @Composable
    fun OutlinedColors(
        backgroundColor: Color = CTrackerTheme.colors.background,
        disabledColor: Color = DisabledColor(),
        contentColor: Color = ContrastColor(),
        disabledContentColor: Color = ContrastColor(),
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledBackgroundColor = disabledColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

}

@Preview(showBackground = true)
@Composable
fun CTPrimaryButtonPreview() {
    CalorieTrackerTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CTPrimaryButton.Outlined(
                onClick = { },
                text = "Outlined",
                icon = Icons.Default.Add
            )
            CTPrimaryButton.Filled(
                onClick = { },
                text = "Filled",
            )
        }
    }
}


