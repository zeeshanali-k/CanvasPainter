package com.devscion.canvaspainter

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.viewinterop.AndroidView
import com.devscion.canvaspainter.models.PaintPath
import com.devscion.canvaspainter.ui.theme.CanvasPainterTheme
import com.devscion.canvaspainter.utils.AppUtils
import com.devscion.canvaspainter.utils.GraphicUtils

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val selectedColor = remember {
                mutableStateOf(AppUtils.PENS[0])
            }

            val isSaveRequested = remember {
                mutableStateOf(false)
            }
            val path = remember {
                mutableStateOf(listOf<PaintPath>())
            }

            CanvasPainterTheme {
                Scaffold {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
//                        Paint Section
                        Column(Modifier.fillMaxSize()) {
                            CanvasPainter(
                                path, selectedColor, Modifier.fillMaxWidth()
                                    .weight(1f)
                            )
                            PensSection(selectedPos = selectedColor)
                        }
//                        Other
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
//                                    TODO    GraphicUtils.createBitmapAndSave(
//                                            ctx, this,
//                                            path.value.asAndroidPath()
//                                        )
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
