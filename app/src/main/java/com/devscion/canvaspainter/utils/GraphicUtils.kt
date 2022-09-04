package com.devscion.canvaspainter.utils
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.util.Log
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.graphics.drawscope.Stroke
//import com.devscion.canvaspainter.models.PaintPath
//
//object GraphicUtils {
//    private const val TAG = "GraphicUtils"
//    const val STROKE_WIDTH = 5.0f
//
//    fun createPath(points: List<Offset>) = Path().apply {
//        if (points.size > 1) {
//            var oldPoint: Offset? = null
//            this.moveTo(points[0].x, points[0].y)
//            for (i in 1 until points.size) {
//                val point: Offset = points[i]
//                oldPoint?.let {
//                    val midPoint = calculateMidpoint(it, point)
//                    if (i == 1) {
//                        this.lineTo(midPoint.x, midPoint.y)
//                    } else {
//                        this.quadraticBezierTo(it.x, it.y, midPoint.x, midPoint.y)
//                    }
//                }
//                oldPoint = point
//            }
//            oldPoint?.let { this.lineTo(it.x, oldPoint.y) }
//        }
//    }
//
//    private fun calculateMidpoint(start: Offset, end: Offset) =
//        Offset((start.x + end.x) / 2, (start.y + end.y) / 2)
//
//    private val nativePaint =
//        Paint().apply {
//            isDither = true
//            style = Paint.Style.STROKE
//            color = Color.Black.toArgb()
//            isAntiAlias = true
//            strokeCap = Paint.Cap.BUTT
//            strokeWidth = STROKE_WIDTH
//        }
//
//    fun createBitmapAndSave(
//        context: Context,
//        parent: LinearLayout,
//        path: List<PaintPath>
//    ) {
//        val nativeBMap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ARGB_8888)
////        Log.d(TAG, "createBitmapAndSave: ${nativeBMap.width} : ${nativeBMap.height}")
//        parent.measure(
//            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
//            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)
//        )
//        val canvas = Canvas(nativeBMap)
//        canvas.drawColor(Color.White.toArgb())
//        for (p in path)
//            canvas.drawPath(
//                createPath(p.points).asAndroidPath(),
//                Paint().apply {
//                    isDither = true
//                    style = Paint.Style.STROKE
//                    color = p.color.toArgb()
//                    isAntiAlias = true
//                    strokeCap = Paint.Cap.BUTT
//                    strokeWidth = STROKE_WIDTH
//                }
//            )
//        parent.draw(canvas)
//        AppUtils.saveBitmap(context, nativeBMap)
//
//    }
//
//    fun createBitmapFromView(view: View, width: Int, height: Int): Bitmap {
//        view.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.MATCH_PARENT
//        )
//
//        view.measure(
//            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
//            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
//        )
//
//        view.layout(0, 0, width, height)
//
//        val canvas = Canvas()
//        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//
//        canvas.setBitmap(bitmap)
//        view.draw(canvas)
//
//        return bitmap
//    }
//}