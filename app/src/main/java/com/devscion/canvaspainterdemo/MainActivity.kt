package com.devscion.canvaspainterdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.devscion.canvaspainter.CanvasPainter
import com.devscion.canvaspainter.rememberCanvasPainterController
import com.devscion.canvaspainterdemo.ui.theme.CanvasPainterTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painterController = rememberCanvasPainterController(maxStrokeWidth = 200f)
            CanvasPainterTheme {
                CanvasPainter(
                    Modifier.fillMaxSize(),
                    painterController
                )
            }
        }
    }
}
