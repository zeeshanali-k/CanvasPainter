package com.devscion.canvaspainter

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.motionEventSpy
import kotlin.math.absoluteValue

private const val TAG = "CanvasPainter"
private const val TOLERANCE = 4.0

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CanvasPainter(
    path: MutableState<Path>
) {
    val startPos = remember { mutableStateOf(Offset.Zero) }

    val tempPath by remember { //for remembering start position of touch
        mutableStateOf(Path())
    }
    Canvas(Modifier.fillMaxSize()
        .clipToBounds()
        .background(Color.White)
        .motionEventSpy {
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
                    path.value = Path().apply {
                        addPath(path.value)
                        addPath(tempPath)
                    }
                    startPos.value = Offset(it.x, it.y)
                }
            }
        }
        .drawBehind {
            drawPath(
                path.value,
                brush = Brush.linearGradient(listOf(Color.Black, Color.Black)),
                style = Stroke(width = 7.0f, cap = StrokeCap.Round),
            )
        }) {}
}