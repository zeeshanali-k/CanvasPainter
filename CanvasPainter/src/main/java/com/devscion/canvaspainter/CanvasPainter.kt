package com.devscion.canvaspainter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.devscion.canvaspainter.components.PainterToolbar

@Composable
fun CanvasPainter(
    modifier: Modifier = Modifier,
    painterController: PainterController
) {
    Box(modifier, contentAlignment = Alignment.TopStart) {
//                        Paint Section
        Column(Modifier.fillMaxSize()) {
            if (painterController.showToolbar)
                PainterToolbar(painterController = painterController)
            CanvasPainterBoard(
                painterController,
                Modifier.fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}