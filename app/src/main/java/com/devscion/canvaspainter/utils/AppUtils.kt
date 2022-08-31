package com.devscion.canvaspainter.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import android.os.Environment
import android.provider.MediaStore
import androidx.core.graphics.BitmapCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object AppUtils {
    fun saveBitmap(context: Context, bitmap: Bitmap)  {
//        Getting images path
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
                    "${Environment.DIRECTORY_DCIM}/Canvas Painter"
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