package tech.devscion.canvaspainter

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import tech.devscion.canvaspainter.utils.AppUtils.createPath

@Composable
internal fun CanvasPainterBoard(
    painterController: PainterController,
    modifier: Modifier = Modifier
) {
    val paintPath by painterController.paintPath.collectAsState()
    Spacer(modifier
        .fillMaxSize()
        .clipToBounds()
        .pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = painterController::onDragEnd,
                onDragStart = painterController::addPaintPath,
            ) { change, _ ->
                painterController.updateLastPath(change.position)
            }
        }
        .drawBehind {
            painterController.canvasSize = this.size
            for (p in paintPath) {
                drawPath(
                    createPath(p.points),
                    color = p.color,
                    style = Stroke(
                        width = p.stroke,
                        cap = StrokeCap.Round
                    ),
                )
            }
        }
        .onSizeChanged {
            painterController.canvasSize = it.toSize()
        })
}