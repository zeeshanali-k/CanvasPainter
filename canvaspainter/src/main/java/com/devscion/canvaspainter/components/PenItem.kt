package com.devscion.canvaspainter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devscion.canvaspainter.R
import com.devscion.canvaspainter.models.PaintBrush

@Composable
fun PenItem(modifier: Modifier = Modifier,paintBrush: PaintBrush) {
    Box {
        Image(
            painterResource(R.drawable.ic_baseline_brush_24),
            modifier = modifier.width(60.dp)
                .rotate(135f),
            alignment = Alignment.Center,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(paintBrush.color)
        )
    }
}

//@Preview
//@Composable
//fun Prev(){
//    PenItem(Color.Blue)
//}