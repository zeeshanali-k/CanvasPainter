package com.devscion.canvaspainter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.devscion.canvaspainter.models.PaintBrush
import com.devscion.canvaspainter.models.PaintPath
import com.devscion.canvaspainter.models.StorageOptions
import com.devscion.canvaspainter.utils.AppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.NullPointerException

@Composable
fun rememberCanvasPainterController(
    maxStrokeWidth: Float = 50f,
    showToolbar: Boolean = true,
    storageOptions: StorageOptions = StorageOptions(),
    color: Color? = null,
    onBitmapGenerated: ((bitmap: Bitmap) -> Unit)? = null,
) = remember {
    PainterController(maxStrokeWidth, showToolbar, storageOptions, onBitmapGenerated).apply {
        color?.let {
            setCustomColor(color)
        }
    }
}

class PainterController(
    var maxStrokeWidth: Float = 50f,
    var showToolbar: Boolean = true,
    var storageOptions: StorageOptions = StorageOptions(),
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
        if (paintPath.value.isEmpty()) {
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val view = canvasPaintView.value!!
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.White.toArgb())
            view.draw(canvas)

            if (storageOptions.shouldSaveByDefault) {
                try {

                    AppUtils.saveBitmap(view.context, bitmap, storageOptions)
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            view.context, "Cannot Save Painting",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(view.context, "Saved", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                onBitmapGenerated?.invoke(bitmap)
                    ?: throw NullPointerException(view.context.getString(R.string.invalid_interface))
            }
        }
    }

    fun setStrokeWidth(width: Float) {
        strokeWidth.value = width
    }

    fun setCustomColor(color: Color) {
        selectedColor.value = PaintBrush(-1, color)
    }

    fun undo() {
        paintPath.value = paintPath.value.toMutableList().apply {
            undonePath.value = undonePath.value
                .toMutableList().apply {
                    add(paintPath.value.last())
                }.toList()
            this.removeLast()
        }.toList()
    }

    fun generateBitmap(): Bitmap {
        val view = canvasPaintView.value!!
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.White.toArgb())
        view.draw(canvas)
        return bitmap
    }

    fun redo() {
        paintPath.value = paintPath.value.toMutableList().apply {
            add(undonePath.value.last())
            undonePath.value = undonePath.value
                .toMutableList().apply { removeLast() }.toList()
        }.toList()
    }

    internal fun toggleStrokeSelection() {
        isStrokeSelection.value = isStrokeSelection.value.not()
    }


    fun reset() {
        undonePath.value = paintPath.value
        paintPath.value = listOf()
    }

}