package tech.devscion.canvaspainter_sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import tech.devscion.canvaspainter.CanvasPainter
import tech.devscion.canvaspainter.rememberCanvasPainterController

@Composable
fun App() {
    MaterialTheme {
        val painterController = rememberCanvasPainterController(
            onPathUpdate = {}
        )
        val showImage = remember {
            mutableStateOf(false)
        }
        val density = LocalDensity.current
        Scaffold(
            Modifier.fillMaxSize()
                .navigationBarsPadding(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (painterController.isCanvasEmpty().not()) {
                            showImage.value = showImage.value.not()
                        }
                    }
                ) {
                    Icon(
                        if (showImage.value) Icons.Default.Delete
                        else Icons.Default.Create, ""
                    )
                }
            }
        ) {
            Column(
                Modifier.fillMaxWidth().padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showImage.value) {
                    Image(
                        remember {
                            painterController.getCanvasAsImageBitmap(
                                canvasDensity = density,
                            )
                        },
                        "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    CanvasPainter(
                        modifier = Modifier.fillMaxSize(),
                        painterController = painterController,
                    )
                }
            }
        }
    }
}