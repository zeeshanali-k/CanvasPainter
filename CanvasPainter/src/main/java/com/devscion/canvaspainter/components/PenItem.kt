package com.devscion.canvaspainter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
fun PenItem(modifier: Modifier = Modifier, paintBrush: PaintBrush) {
    Box {
        Image(
            painterResource(if (paintBrush.id == -1) R.drawable.color_wheel else R.drawable.ic_baseline_brush_24),
            modifier = modifier.width(if (paintBrush.id == -1) 50.dp else 60.dp)
                .rotate(135f)
                .padding(horizontal = if (paintBrush.id == -1) 10.dp else 0.dp),
            alignment = Alignment.Center,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = if (paintBrush.id == -1) null else ColorFilter.tint(paintBrush.color)
        )
    }
}

//@Preview
//@Composable
//fun Prev(){
//    PenItem(Color.Blue)
//}