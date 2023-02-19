package com.luisma.tracker_ui.pages.tracker_search_food.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText

@Composable
fun SearchEmpty(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CTText.H2(
            text = stringResource(
                id = R.string.no_results
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchEmptyPreview() {
    SearchEmpty()
}