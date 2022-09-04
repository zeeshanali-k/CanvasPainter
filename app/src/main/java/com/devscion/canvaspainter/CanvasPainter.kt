package com.devscion.canvaspainter

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.input.pointer.pointerInput
import com.devscion.canvaspainter.models.PaintBrush
import com.devscion.canvaspainter.models.PaintPath
import com.devscion.canvaspainter.utils.AppUtils
import com.devscion.canvaspainter.utils.GraphicUtils.createPath
import kotlin.math.absoluteValue

private const val TAG = "CanvasPainter"
private const val TOLERANCE = 4.0

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CanvasPainter(
    path: MutableState<List<PaintPath>>,
    selectedColor: MutableState<PaintBrush>,
    modifier: Modifier = Modifier
) {
    val startPos = remember { mutableStateOf(Offset.Zero) }

    val tempPath by remember { //for remembering start position of touch
        mutableStateOf(Path())
    }
    Canvas(modifier
        .clipToBounds()
        .background(Color.White)
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = {
                path.value = path.value.toMutableList().apply {
                    add(
                        PaintPath(
                            mutableStateListOf(it),
                            selectedColor.value.color
                        )
                    )
                }
            }) { change, dragAmount ->
                val index = path.value.lastIndex
                path.value[index].points.add(change.position)
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
                    style = Stroke(width = 7.0f, cap = StrokeCap.Round),
                )

        }) {}
}

