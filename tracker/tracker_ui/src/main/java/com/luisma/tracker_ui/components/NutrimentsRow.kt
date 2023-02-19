package com.luisma.tracker_ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTSpace
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun NutrimentsRow(
    modifier: Modifier = Modifier,
    carbs: Int,
    proteins: Int,
    fats: Int,
) {
    Row(
        modifier = modifier,
    ) {
        Nutriment(
            modifier = Modifier.padding(end = CTSpace.space1),
            name = stringResource(id = R.string.carbs),
            grams = carbs
        )
        Nutriment(
            modifier = Modifier.padding(end = CTSpace.space1),
            name = stringResource(id = R.string.protein),
            grams = proteins
        )
        Nutriment(
            name = stringResource(id = R.string.fat),
            grams = fats
        )
    }
}

@Composable
private fun Nutriment(
    modifier: Modifier = Modifier,
    name: String,
    grams: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            CTText.H5(
                modifier = Modifier
                    .padding(end = CTSpace.space1)
                    .alignBy(LastBaseline),
                text = grams.toString(),
            )
            CTText.Caption(
                modifier = Modifier
                    .padding(end = CTSpace.space1)
                    .alignBy(LastBaseline),
                text = stringResource(id = R.string.grams)
            )
        }
        CTText.Body1(
            text = name,
            textStyle = CTrackerTheme.typography.body1.copy(
                fontWeight = FontWeight.Light
            )
        )
    }
}