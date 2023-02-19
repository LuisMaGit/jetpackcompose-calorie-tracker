package com.luisma.onboarding_ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisma.core_ui.components.CTSpacerH
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun NumericTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    onSubmit: () -> Unit = {},
    imeAction: ImeAction = ImeAction.Done,
    caption: String,
    textStyle: TextStyle = CTrackerTheme.typography.h2.copy(
        color = CTrackerTheme.colors.darkGreen,
        fontSize = 70.sp
    ),
    errorText: String = "",
    showError: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier,
        ) {
            BasicTextField(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .alignBy(LastBaseline),
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSubmit()
                    },
                ),
                textStyle = textStyle,
            )
            CTSpacerH.Base2()
            CTText.H4(
                text = caption,
                modifier = Modifier
                    .alignBy(LastBaseline)
            )
        }
        if (showError) {
            CTText.Body1(
                text = errorText,
                modifier = Modifier.padding(12.dp),
                textStyle = CTrackerTheme.typography.body1.copy(
                    color = CTrackerTheme.colors.redError,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}