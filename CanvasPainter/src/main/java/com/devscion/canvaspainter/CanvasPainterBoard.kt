package com.devscion.canvaspainter

import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import com.devscion.canvaspainter.utils.AppUtils.createPath

private const val TAG = "CanvasPainter"

@Composable
internal fun CanvasPainterBoard(
    painterController: PainterController,
    modifier: Modifier = Modifier
) = AndroidView(modifier = modifier, factory = {
    ComposeView(it).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContent {
            val path = painterController.paintPath.collectAsState()
            Canvas(Modifier
                .fillMaxSize()
                .clipToBounds()
                .pointerInput(Unit) {
                    detectDragGestures(onDragStart = { offset ->
                        painterController.addPath(offset)
                    }) { change, _ ->
                        painterController.updateLastPath(change.position)
                    }
                }
                .drawBehind {
                    for (p in path.value)
                        drawPath(
                            createPath(p.points),
                            color = p.color,
                            style = Stroke(
                                width = p.stroke,
                                cap = StrokeCap.Round
                            ),
                        )

                }) {}
        }
        painterController.updateCanvasPaintView(this)
    }
})

