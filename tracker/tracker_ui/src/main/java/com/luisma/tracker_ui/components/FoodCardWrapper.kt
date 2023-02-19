package com.luisma.tracker_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.components.CTNetworkImage

object FoodCardWrapper {
    @Composable
    fun WithVectorImage(
        modifier: Modifier = Modifier,
        image: Int,
        content: @Composable () -> Unit,
    ) {
        Wrapper(
            modifier = modifier,
            imageContent = {
                Image(
                    painter = painterResource(image),
                    contentDescription = null
                )
            },
            content = content
        )
    }

    @Composable
    fun WithNetworkImage(
        modifier: Modifier = Modifier,
        url: String,
        content: @Composable () -> Unit,
    ) {
        Wrapper(
            modifier = modifier,
            imageContent = {
                CTNetworkImage(
                    modifier = Modifier.fillMaxSize(),
                    url = url
                )
            },
            content = content
        )
    }


    @Composable
    private fun Wrapper(
        modifier: Modifier = Modifier,
        imageContent: @Composable () -> Unit,
        content: @Composable () -> Unit,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(.3f),
                contentAlignment = Alignment.Center
            ) {
                imageContent()
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(.7f)
            ) {
                content()
            }
        }
    }
}