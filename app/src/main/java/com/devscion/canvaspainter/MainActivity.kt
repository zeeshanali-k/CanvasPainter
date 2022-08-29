package com.devscion.canvaspainter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import com.devscion.canvaspainter.ui.theme.CanvasPainterTheme
import com.devscion.canvaspainter.utils.GraphicUtils

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sHeight = LocalConfiguration.current.screenWidthDp
            val sWidth = LocalConfiguration.current.screenHeightDp
            val isSaveRequested = remember {
                mutableStateOf(false)
            }
            CanvasPainterTheme {
                Scaffold {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
                        CanvasPainter()
                        Button(onClick = {
                            isSaveRequested.value = true
                        }) {
                            Text("Save")
                        }
                        if (isSaveRequested.value) {
                            Log.d(TAG, "onCreate: ${isSaveRequested.value}")
                            CanvasPainterBitmap(sHeight, sWidth)
                            isSaveRequested.value = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CanvasPainterBitmap(sHeight: Int, sWidth: Int) {
    Log.d(TAG, "CanvasPainterBitmap: ")
    AndroidView({
        LinearLayout(it).apply {
            layoutParams = LinearLayout.LayoutParams(
                sWidth.toFloat().toInt(),
                sHeight.toFloat().toInt(),
            )
            visibility = View.GONE
            val canvasPainterView = ComposeView(it).apply {
                layoutParams = LinearLayout.LayoutParams(
                    sWidth,
                    sHeight,
                )
            }

            addView(canvasPainterView)
            canvasPainterView.setContent {
                CanvasPainter()
            }

            viewTreeObserver.addOnGlobalLayoutListener {
                GraphicUtils.createBitmapFromView(canvasPainterView, sWidth, sHeight).let { bm ->
                }
                removeView(canvasPainterView)
            }
        }
    })
}

//@Preview
//@Composable
//fun Test(){
//    CanvasPainter()
//}