package com.devscion.canvaspainter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import com.devscion.canvaspainter.ui.theme.CanvasPainterTheme
import com.devscion.canvaspainter.utils.GraphicUtils

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val isSaveRequested = remember {
                mutableStateOf(false)
            }
            val path = remember {
                mutableStateOf(Path())
            }

            CanvasPainterTheme {
                Scaffold {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
                        CanvasPainter(path)
                        Button(onClick = {
                            isSaveRequested.value = true
                        }) {
                            Text("Save")
                        }
                        if (isSaveRequested.value) {
                            AndroidView(modifier = Modifier.fillMaxSize(), factory = { ctx ->
                                val parent = LinearLayout(ctx).apply {
                                    layoutParams = LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                    )
                                    visibility = View.GONE
                                    viewTreeObserver.addOnGlobalLayoutListener {
                                        GraphicUtils.createBitmapAndSave(
                                            ctx, this,
                                            path.value.asAndroidPath()
                                        )
                                    }
                                }
                                parent
                            })
                            isSaveRequested.value = false
                        }
                    }
                }
            }
        }
    }
}
