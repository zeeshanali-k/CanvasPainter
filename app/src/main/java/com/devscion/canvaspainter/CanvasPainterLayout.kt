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


@Composable
fun CanvasPainterLayout(
    modifier: Modifier = Modifier,
    painterController: PainterController
) {
    Scaffold(modifier = modifier) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
//                        Paint Section
            Column(Modifier.fillMaxSize()) {
                CanvasPainter(
                    painterController,
                    Modifier.fillMaxWidth()
                        .weight(1f)
                )
                PensSection(painterController = painterController)
            }
//                        Other
            Button(onClick = {
                painterController.saveBitmap()
            }) {
                Text("Save")
            }

        }
    }
}