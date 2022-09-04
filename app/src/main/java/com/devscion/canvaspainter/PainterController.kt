package com.devscion.canvaspainter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.devscion.canvaspainter.models.PaintBrush
import com.devscion.canvaspainter.models.PaintPath
import com.devscion.canvaspainter.utils.AppUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class PainterController() {
    companion object {
        const val TOLERANCE = 4.0
        var STROKE_WIDTH: Float = 5.0f
    }

    val paintPath = MutableStateFlow(listOf<PaintPath>())
    val selectedColor = MutableStateFlow(AppUtils.PENS[0])
    private val canvasPaintView = MutableStateFlow<View?>(null)

    fun updateCanvasPaintView(view: View) {
        canvasPaintView.value = view
    }

    fun updateSelectedColor(paintBrush: PaintBrush) {
        selectedColor.value = paintBrush
    }

    fun addPath(offset: Offset) {
        paintPath.update {
            it.toMutableList().apply {
                add(
                    PaintPath(
                        mutableStateListOf(offset),
                        selectedColor.value.color
                    )
                )
            }
        }
    }

    fun updateLastPath(offset: Offset) {
        paintPath.update {
            it.apply {
                last().apply {
                    points.add(offset)
                }
            }
        }
    }

    fun saveBitmap() {
        val view = canvasPaintView.value!!
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.White.toArgb())
        view.draw(canvas)

        AppUtils.saveBitmap(view.context, bitmap)
    }

}