package com.devscion.canvaspainter.utils

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.Q
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.devscion.canvaspainter.models.PaintBrush
import com.devscion.canvaspainter.models.StorageOptions
import java.io.FileOutputStream
import java.io.IOException
import java.util.jar.Manifest

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


    fun saveBitmap(context: Context, bitmap: Bitmap, storageOptions: StorageOptions) {
//        Getting images path
        if (SDK_INT >= M && context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            throw IOException("Storage permission is not granted")
        }
        val imagesCollection =
            if (SDK_INT >= Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val imgData = ContentValues().apply {
            put(
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                "painting_${System.currentTimeMillis()}.png"
            )
            if (SDK_INT >= Q) {
                put(
                    MediaStore.Images.ImageColumns.RELATIVE_PATH,
                    "${Environment.DIRECTORY_DCIM}/${storageOptions.saveDirectoryName}"
                )
                put(MediaStore.Images.ImageColumns.IS_PENDING, 1)
            }
        }

        val imgResolver = context.contentResolver
        val imgUri = context.contentResolver.insert(imagesCollection, imgData) ?: return
        imgResolver.openFileDescriptor(imgUri, "w").apply {
            val fs = FileOutputStream(this!!.fileDescriptor)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fs)
        }
        if (SDK_INT >= Q) {
            imgData.clear()
            imgData.put(MediaStore.Images.ImageColumns.IS_PENDING, 0)
        }
        imgResolver.update(imgUri, imgData, null, null)

    }
}