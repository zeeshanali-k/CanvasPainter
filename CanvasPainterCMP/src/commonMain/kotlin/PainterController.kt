package com.devscion.canvaspainter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.IntSize
import com.devscion.canvaspainter.models.PaintBrush
import com.devscion.canvaspainter.models.PaintPath
import com.devscion.canvaspainter.utils.AppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Canvas

@Composable
fun rememberCanvasPainterController(
    maxStrokeWidth: Float = 50f,
    showToolbar: Boolean = true,
    color: Color? = null,
    onBitmapGenerated: ((bitmap: Bitmap) -> Unit)? = null,
) = remember {
    PainterController(maxStrokeWidth, showToolbar, onBitmapGenerated).apply {
        color?.let {
            setCustomColor(color)
        }
    }
}

class PainterController(
    var maxStrokeWidth: Float = 50f,
    var showToolbar: Boolean = true,
    private val onBitmapGenerated: ((bitmap: Bitmap) -> Unit)? = null,
) {
    private val TAG = "PainterController"

    //  Variables
//    var onBitmapGenerated: OnBitmapGenerated? = null

    internal val strokeWidth = MutableStateFlow(5f)
    internal val isStrokeSelection = MutableStateFlow(false)

    internal val paintPath = MutableStateFlow(listOf<PaintPath>())
    internal val undonePath = MutableStateFlow(listOf<PaintPath>())
    internal val selectedColor = MutableStateFlow(AppUtils.PENS[1])

//    private val canvasPaintView = MutableStateFlow<View?>(null)
//
//    internal fun updateCanvasPaintView(view: View) {
//        canvasPaintView.value = view
//    }

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

    internal fun saveBitmap(canvas: IntSize) {
        if (paintPath.value.isEmpty()) {
            return
        }
        CoroutineScope(Dispatchers.Default).launch {
            val bitmap = ImageBitmap(canvas.width, canvas.height, ImageBitmapConfig.Argb8888)
            val canvas = Canvas(bitmap.asSkiaBitmap())
//            canvas.draw(canvas)

            onBitmapGenerated?.invoke(bitmap.asSkiaBitmap())
                ?: throw NullPointerException("Invalid Bitmap")
        }
    }


    fun setStrokeWidth(width: Float) {
        strokeWidth.value = width
    }

    fun setCustomColor(color: Color) {
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

    internal fun toggleStrokeSelection() {
        isStrokeSelection.value = isStrokeSelection.value.not()
    }


    internal fun reset() {
        undonePath.value = paintPath.value
        paintPath.value = listOf()
    }

}