package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.services.TimeHelperUIService
import com.luisma.core_ui.theme.CTSpace
import java.time.LocalDate

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    tapLeft: () -> Unit,
    tapRight: () -> Unit,
    date: LocalDate,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Arrow(
            imageVector = Icons.Rounded.ArrowBack,
            onTap = tapLeft,
        )
        CTText.H3(text = TimeHelperUIService.dateLabel(date = date))
        Arrow(
            imageVector = Icons.Rounded.ArrowForward,
            onTap = tapRight,
        )
    }
}

@Composable
private fun Arrow(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onTap: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onTap() }
            .padding(CTSpace.space1)
    ) {
        Icon(
            imageVector,
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    DatePicker(
        tapLeft = {},
        tapRight = {},
        date = LocalDate.now().plusDays(4)
    )
}