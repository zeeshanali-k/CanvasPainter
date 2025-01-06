package tech.devscion.canvaspainter.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import tech.devscion.canvaspainter.models.PaintBrush
import tech.devscion.canvaspainter.models.PaintPath

internal object AppUtils {

    val PEN_COLORS = listOf(
        Color.Black, Color.Red, Color.Blue, Color.Green,
        Color.Gray, Color.DarkGray, Color.DarkGray,
        Color.Cyan, Color.Magenta, Color.Yellow
    )

    val PENS = MutableList(PEN_COLORS.size) {
        PaintBrush(it, PEN_COLORS[it])
    }.apply {
        add(0, PaintBrush(color = Color.Transparent))
    }.toList()

    fun createPath(points: List<Offset>) = Path().apply {
        if (points.size > 1) {
            var oldPoint: Offset? = null
            this.moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                val point: Offset = points[i]
                oldPoint?.let {
                    val midPoint = calculateMidpoint(it, point)
                    if (i == 1) {
                        this.lineTo(midPoint.x, midPoint.y)
                    } else {
                        this.quadraticTo(it.x, it.y, midPoint.x, midPoint.y)
                    }
                }
                oldPoint = point
            }
            oldPoint?.let { this.lineTo(it.x, oldPoint.y) }
        }
    }

    private fun calculateMidpoint(start: Offset, end: Offset) =
        Offset((start.x + end.x) / 2, (start.y + end.y) / 2)

    fun getPathImageBitmap(
        path: List<PaintPath>,
        size: Size,
        canvasDensity: Density,
    ): ImageBitmap {
        val drawScope = CanvasDrawScope()
        val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt())
        val canvas = Canvas(bitmap)

        drawScope.draw(
            density = canvasDensity,
            layoutDirection = LayoutDirection.Ltr,
            canvas = canvas,
            size = size,
        ) {
            path.forEach { p ->
                drawPath(
                    path = createPath(p.points),
                    color = p.color,
                    style = Stroke(
                        width = p.stroke,
                        cap = StrokeCap.Round
                    ),
                )
            }
        }
        return bitmap
    }

}

expect fun ImageBitmap.toByteArray(): ByteArray?