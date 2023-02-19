package com.luisma.tracker_ui.pages.tracker_search_food.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTSpace
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    focusRequester: FocusRequester,
    onSubmit: () -> Unit
) {
    var showFocusBorder by remember { mutableStateOf(false) }
    val color = CTrackerTheme.colors

    fun submit() {
        if (value.isNotEmpty()) {
            onSubmit()
        }
    }

    @Composable
    fun stateColor(): Color {
        if (!enabled) {
            return color.shade3
        }

        if (showFocusBorder) {
            return color.darkGreen
        }

        return color.inverseBackground
    }

    @Composable
    fun textColor(): Color {
        return if (enabled) color.inverseBackground else color.shade3
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                brush = SolidColor(stateColor()),
                shape = RoundedCornerShape(CTSpace.space1)
            )
            .padding(
                horizontal = CTSpace.space2,
                vertical = CTSpace.space3
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .onFocusEvent {
                    showFocusBorder = it.isFocused
                },
            value = value,
            maxLines = 1,
            onValueChange = onValueChange,
            textStyle = CTrackerTheme.typography.h5.copy(
                color = textColor(),

                ),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    CTText.H5(
                        text = stringResource(id = R.string.search),
                        maxLines = 1,
                        textStyle = CTrackerTheme.typography.h5.copy(
                            color = textColor(),
                        )
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    submit()
                }
            ),
            enabled = enabled,
        )
        if (value.isNotEmpty()) {
            Icon(
                modifier = Modifier.clickable { onValueChange("") },
                imageVector = Icons.Filled.Close,
                contentDescription = "",
                tint = stateColor()
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "",
                tint = stateColor()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchFormPreview() {
    var text by remember { mutableStateOf("hello") }

    val focusRequester = FocusRequester()
    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
    }
    SearchForm(
        value = text,
        onValueChange = {
            text = it
        },
        focusRequester = focusRequester,
        enabled = false,
        onSubmit = {}
    )
}