package com.devscion.canvaspainter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.devscion.canvaspainter.interfaces.OnBitmapGenerated
import com.devscion.canvaspainter.models.PaintBrush
import com.devscion.canvaspainter.models.PaintPath
import com.devscion.canvaspainter.models.StorageOptions
import com.devscion.canvaspainter.utils.AppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class PainterController {
    private val TAG = "PainterController"

    //  Variables
    var onBitmapGenerated: OnBitmapGenerated? = null
    var maxStrokeWidth = 50f
    var showToolbar = true
    var storageOptions = StorageOptions()


    internal val strokeWidth = MutableStateFlow(5f)
    internal val isStrokeSelection = MutableStateFlow(false)

    internal val paintPath = MutableStateFlow(listOf<PaintPath>())
    internal val undonePath = MutableStateFlow(listOf<PaintPath>())
    val selectedColor = MutableStateFlow(AppUtils.PENS[1])

    private val canvasPaintView = MutableStateFlow<View?>(null)

    internal fun updateCanvasPaintView(view: View) {
        canvasPaintView.value = view
    }

    fun setPaintColor(paintBrush: PaintBrush) {
        selectedColor.value = paintBrush
    }

    internal fun addPath(offset: Offset) {
        undonePath.value = emptyList()
        paintPath.update {
            it.toMutableList().apply {
                add(
                    PaintPath(
                        mutableStateListOf(offset),
                        strokeWidth.value,
                        selectedColor.value.color
                    )
                )
            }
        }
    }

    internal fun updateLastPath(offset: Offset) {
        paintPath.update {
            it.apply {
                last().apply {
                    points.add(offset)
                }
            }
        }
    }

    internal fun saveBitmap() {
        if(paintPath.value.isEmpty()){
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val view = canvasPaintView.value!!
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.White.toArgb())
            view.draw(canvas)

            if (storageOptions.shouldSaveByDefault) {
                AppUtils.saveBitmap(view.context, bitmap, storageOptions)
                withContext(Dispatchers.Main) {
                    Toast.makeText(view.context, "Saved", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                onBitmapGenerated?.onBitmap(bitmap)
                    ?: throw NullPointerException(view.context.getString(R.string.invalid_interface))
            }
        }
    }

    fun setStrokeWidth(width: Float) {
        strokeWidth.value = width
    }

    internal fun setCustomColor(color: Color) {
        selectedColor.value = PaintBrush(-1, color)
    }

    internal fun undo() {
        paintPath.value = paintPath.value.toMutableList().apply {
            undonePath.value = undonePath.value
                .toMutableList().apply {
                    add(paintPath.value.last())
                }.toList()
            this.removeLast()
        }.toList()
    }

    internal fun redo() {
        paintPath.value = paintPath.value.toMutableList().apply {
            add(undonePath.value.last())
            undonePath.value = undonePath.value
                .toMutableList().apply { removeLast() }.toList()
        }.toList()
    }

    fun toggleStrokeSelection() {
        isStrokeSelection.value = isStrokeSelection.value.not()
    }

}