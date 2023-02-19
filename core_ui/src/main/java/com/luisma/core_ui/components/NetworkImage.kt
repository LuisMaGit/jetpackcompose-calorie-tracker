package com.luisma.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun CTNetworkImage(
    modifier: Modifier = Modifier,
    url: String,
) {

    val painter = rememberAsyncImagePainter(url)

    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )

    if (painter.state is AsyncImagePainter.State.Loading) {
        Loading()
    }

    if (painter.state is AsyncImagePainter.State.Empty) {
        Error()
    }

    if (painter.state is AsyncImagePainter.State.Error) {
        Error()
    }

}


@Composable
private fun Loading() {
    CTLoader()
}

@Composable
private fun Error() {
    Icon(
        imageVector = Icons.Default.Close,
        tint = CTrackerTheme.colors.redError,
        contentDescription = ""
    )
}

@Preview(showBackground = true)
@Composable
fun CTNetworkImagePreview() {
    CTNetworkImage(url = "https://flutter.github.io/assets-for-api-docs/assets/widgets/owl.jpg")
}