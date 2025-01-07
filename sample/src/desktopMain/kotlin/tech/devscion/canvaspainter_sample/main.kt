package tech.devscion.canvaspainter_sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Canvas Painter",
    ) {
        App()
    }
}