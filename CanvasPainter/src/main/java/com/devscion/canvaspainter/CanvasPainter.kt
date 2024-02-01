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
//                        Paint Section
    Column(modifier) {
        if (painterController.showToolbar)
            PainterToolbar(painterController = painterController)
        CanvasPainterBoard(
            painterController,
            modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}