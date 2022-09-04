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
fun CanvasPainter(
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
                .background(Color.White)
                .pointerInput(Unit) {
                    detectDragGestures(onDragStart = {offset->
                        painterController.addPath(offset)
                    }) { change, dragAmount ->
                        painterController.updateLastPath(change.position)
                    }
                }
                /*.motionEventSpy {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startPos.value = Offset(it.x, it.y)
                            tempPath.moveTo(startPos.value.x, startPos.value.y)
                        }
                        MotionEvent.ACTION_UP -> {
                            tempPath.reset()
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (it.x.minus(startPos.value.x).absoluteValue < TOLERANCE &&
                                it.y.minus(startPos.value.y).absoluteValue < TOLERANCE
                            ) {
                                return@motionEventSpy
                            }
                            tempPath.quadraticBezierTo(
                                startPos.value.x,
                                startPos.value.y,
                                (it.x + startPos.value.x) / 2,
                                (it.y + startPos.value.y) / 2
                            )
                            path.value = path.value.toMutableList().apply {
                                add(
                                    PaintPath(
                                        mutableStateListOf(
                                            Offset(
                                                (it.x + startPos.value.x) / 2,
                                                (it.y + startPos.value.y) / 2
                                            )
                                        ), selectedColor.value.color
                                    )
                                )
                            }
        //                        Path().apply {
        //                        addPath(path.value)
        //                        addPath(tempPath)
        //                    }
                            startPos.value = Offset(it.x, it.y)
                        }
                    }
                }*/
                .drawBehind {
//            Log.d(TAG, "CanvasPainter: ${path.value.size}")

                    for (p in path.value)
                        drawPath(
                            createPath(p.points),
                            color = p.color,
                            style = Stroke(
                                width = PainterController.STROKE_WIDTH,
                                cap = StrokeCap.Round
                            ),
                        )

                }) {}
        }
        painterController.updateCanvasPaintView(this)
    }
})

