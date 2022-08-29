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
import androidx.compose.ui.input.pointer.motionEventSpy

private const val TAG = "CanvasPainter"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CanvasPainter() {

    val startPos = remember { mutableStateOf(Offset.Zero) }
    var path by remember {
        mutableStateOf(Path())
    }
    val tempPath by remember {
        mutableStateOf(Path())
    }

    Canvas(Modifier.fillMaxSize()
        .clipToBounds()
        .background(Color.White)
        .motionEventSpy {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPos.value = Offset(it.x, it.y)
                    tempPath.moveTo(startPos.value.x,startPos.value.y)
                }
                MotionEvent.ACTION_UP -> {
                    tempPath.reset()
                }
                MotionEvent.ACTION_MOVE -> {
                    tempPath.quadraticBezierTo(startPos.value.x, startPos.value.y, it.x, it.y)
                    path = Path().apply {
                        addPath(path)
                        addPath(tempPath)
                    }
                    startPos.value = Offset(it.x, it.y)
                }
            }
        }
        .drawBehind {
            drawPath(
                path,
                brush = Brush.linearGradient(listOf(Color.Black, Color.Gray)),
                style = Stroke(width = 12.0f, cap = StrokeCap.Butt),
            )
        }) {
    }
}