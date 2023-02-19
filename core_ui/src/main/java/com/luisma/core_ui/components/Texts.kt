package com.luisma.core_ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.luisma.core_ui.theme.CTrackerTheme

object CTText {
    @Composable
    fun H1(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = CTrackerTheme.typography.h1,
        maxLines : Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow,
            style =  textStyle,
        )
    }

    @Composable
    fun H2(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = CTrackerTheme.typography.h2,
        maxLines : Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow,
            style =  textStyle,
        )
    }

    @Composable
    fun H3(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = CTrackerTheme.typography.h3,
        maxLines : Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow,
            style =  textStyle,
        )
    }

    @Composable
    fun H4(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = CTrackerTheme.typography.h4,
        maxLines : Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow,
            style =  textStyle,
        )
    }

    @Composable
    fun H5(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = CTrackerTheme.typography.h5,
        maxLines : Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow,
            style =  textStyle,
        )
    }

    @Composable
    fun Body1(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = CTrackerTheme.typography.body1,
        maxLines : Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow,
            style =  textStyle,
        )
    }

    @Composable
    fun Caption(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = CTrackerTheme.typography.caption,
        maxLines : Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow,
            style =  textStyle,
        )
    }

}