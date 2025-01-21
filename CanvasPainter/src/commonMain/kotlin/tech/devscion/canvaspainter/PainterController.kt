package tech.devscion.canvaspainter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import tech.devscion.canvaspainter.models.PaintBrush
import tech.devscion.canvaspainter.models.PaintPath
import tech.devscion.canvaspainter.utils.AppUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tech.devscion.canvaspainter.utils.AppUtils.createPath

@Composable
fun rememberCanvasPainterController(
    maxStrokeWidth: Float = 50f,
    minStrokeWidth: Float = 5f,
    showToolbar: Boolean = true,
    color: Color? = null,
    onPathUpdate: ((Path) -> Unit)? = null,
    onDragStart: (() -> Unit)? = null,
    onDragEnd: (() -> Unit)? = null,
) = remember {
    PainterController(
        maxStrokeWidth = maxStrokeWidth,
        minStrokeWidth = minStrokeWidth,
        showToolbar = showToolbar,
        onPathUpdate = onPathUpdate,
        onDragStart = onDragStart,
        onDragEnd = onDragEnd
    ).apply {
        color?.let {
            setCustomColor(color)
        }
    }
}

class PainterController(
    var maxStrokeWidth: Float = 50f,
    var minStrokeWidth: Float = 5f,
    var showToolbar: Boolean = true,
    private val onPathUpdate: ((Path) -> Unit)? = null,
    private val onDragStart: (() -> Unit)? = null,
    private val onDragEnd: (() -> Unit)? = null,
) {

    internal var canvasSize = Size.Unspecified
    internal val strokeWidth = MutableStateFlow(5f)
    internal val isStrokeSelection = MutableStateFlow(false)

    internal val paintPath = MutableStateFlow(listOf<PaintPath>())
    internal val undonePath = MutableStateFlow(listOf<PaintPath>())
    internal val selectedColor = MutableStateFlow(AppUtils.PENS[1])

    fun setPaintColor(paintBrush: PaintBrush) {
        selectedColor.value = paintBrush
    }

    fun getCurrentPaintPath() = paintPath.value

    fun observePaintPath() = paintPath.asStateFlow()

    internal fun addPaintPath(offset: Offset) {
        onDragStart?.invoke()
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
        onPathUpdate?.invoke(createPath(paintPath.value.last().points))
    }

    internal fun onDragEnd() {
        this.onDragEnd?.invoke()
    }

    internal fun updateLastPath(offset: Offset) {
        paintPath.update {
            it.apply {
                last().apply {
                    points.add(offset)
                }
            }
        }
        onPathUpdate?.invoke(createPath(paintPath.value.last().points))
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

    fun getCanvasAsImageBitmap(
        canvasDensity: Density = Density(1f),
    ): ImageBitmap {
        return AppUtils.getPathImageBitmap(paintPath.value, canvasSize, canvasDensity)
    }

    fun isCanvasEmpty(): Boolean {
        return paintPath.value.isEmpty()
    }

    fun addPaintPath(
        points: List<Offset>,
        color: Color,
        stroke: Float,
    ) {
        if (points.isEmpty()) return
        paintPath.update {
            it.toMutableList().apply {
                add(
                    PaintPath(
                        points = mutableStateListOf<Offset>().apply {
                            addAll(points)
                        },
                        color = color, stroke = stroke,
                    )
                )
            }
        }
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