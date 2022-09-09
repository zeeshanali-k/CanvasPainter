package com.devscion.canvaspainterdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.devscion.canvaspainter.CanvasPainter
import com.devscion.canvaspainter.PainterController
import com.devscion.canvaspainter.models.StorageOptions
import com.devscion.canvaspainterdemo.ui.theme.CanvasPainterTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val painterController = remember {
                PainterController().apply {
                    maxStrokeWidth = 100f
                    showToolbar = true
                    storageOptions = StorageOptions(
                        "My Painter",
                        shouldSaveByDefault = true
                    )
                }
            }
            CanvasPainterTheme {
                CanvasPainter(
                    Modifier.fillMaxSize(),
                    painterController
                )
            }
        }
    }
}
