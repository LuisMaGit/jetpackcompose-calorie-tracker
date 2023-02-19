package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ExpandableBox() {
    var visible by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }
    val animation by animateIntAsState(
        targetValue = count,
        animationSpec = tween(durationMillis = 500)
    )

    Column {
        Button(
            onClick = {
                visible = !visible
            }
        ) {
            Text(text = "TAP")
        }
        AnimatedVisibility(
            visible = visible,
            enter = expandVertically(
                animationSpec = TweenSpec(
                    durationMillis = 200
                ),
                expandFrom = Alignment.Top
            ),
        ) {
            Text(text = "HELLO")
        }
        Button(
            onClick = {
                count = if (count == 0) {
                    100
                } else {
                    0
                }
            }
        ) {
            Text(text = "TAP")
        }

        Text(
            text = animation.toString()
        )
    }

}


@Preview(showBackground = true)
@Composable
fun ExpandableBoxPreview() {
    ExpandableBox()
}