package tech.devscion.canvaspainter.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream


actual fun ImageBitmap.toByteArray(): ByteArray? {
    return try {
        ByteArrayOutputStream().use { stream ->
            this.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.toByteArray()
        }
    } catch (_: Exception) {
        null
    }
}