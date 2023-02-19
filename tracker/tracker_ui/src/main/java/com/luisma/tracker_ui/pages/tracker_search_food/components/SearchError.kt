package com.luisma.tracker_ui.pages.tracker_search_food.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun SearchError(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp),
            imageVector = Icons.Filled.Warning,
            contentDescription = "",
            tint = CTrackerTheme.colors.inverseBackground
        )
        CTText.H2(
            text = stringResource(
                id = R.string.error_unknown
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchErrorPreview() {
    SearchError()
}