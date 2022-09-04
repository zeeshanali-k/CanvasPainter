package com.devscion.canvaspainter.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.toArgb

object GraphicUtils {
    private const val TAG = "GraphicUtils"

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
                        this.quadraticBezierTo(it.x, it.y, midPoint.x, midPoint.y)
                    }
                }
                oldPoint = point
            }
            oldPoint?.let { this.lineTo(it.x, oldPoint.y) }
        }
    }

    private fun calculateMidpoint(start: Offset, end: Offset) =
        Offset((start.x + end.x) / 2, (start.y + end.y) / 2)

    private val nativePaint =
        Paint().apply {
            isDither = true
            style = Paint.Style.STROKE
            color = Color.Black.toArgb()
            isAntiAlias = true
            strokeCap = Paint.Cap.BUTT
            strokeWidth = 5.0f
        }

    fun createBitmapAndSave(
        context: Context,
        parent: LinearLayout,
        path: Path
    ) {
        val nativeBMap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ARGB_8888)
        Log.d(TAG, "createBitmapAndSave: ${nativeBMap.width} : ${nativeBMap.height}")
//        parent.measure(
//            View.MeasureSpec.makeMeasureSpec(sWidth, View.MeasureSpec.EXACTLY),
//            View.MeasureSpec.makeMeasureSpec(sHeight, View.MeasureSpec.EXACTLY)
//        )
//
//        parent.layout(0, 0, sWidth, sHeight)
        val canvas = Canvas(nativeBMap)
        canvas.drawColor(Color.White.toArgb())
        canvas.drawPath(path.asAndroidPath(), nativePaint)
        parent.draw(canvas)
        AppUtils.saveBitmap(context, nativeBMap)

    }

    fun createBitmapFromView(view: View, width: Int, height: Int): Bitmap {
        view.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        view.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )

        view.layout(0, 0, width, height)

        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        canvas.setBitmap(bitmap)
        view.draw(canvas)

        return bitmap
    }
}