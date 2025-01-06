package tech.devscion.canvaspainter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.devscion.canvaspainter.components.PainterToolbar

@Composable
fun CanvasPainter(
    modifier: Modifier = Modifier,
    painterController: PainterController
) {
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