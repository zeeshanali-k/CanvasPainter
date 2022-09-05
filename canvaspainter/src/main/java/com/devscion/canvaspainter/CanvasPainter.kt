package com.devscion.canvaspainter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
            PainterToolbar(painterController = painterController)
            CanvasPainterBoard(
                painterController,
                Modifier.fillMaxWidth()
                    .weight(1f)
            )
            PensSection(painterController = painterController)
        }
    }
}