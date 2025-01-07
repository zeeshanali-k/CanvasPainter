package tech.devscion.canvaspainter.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skiko.toBufferedImage
import java.io.ByteArrayOutputStream


actual fun ImageBitmap.toByteArray(): ByteArray? {
    val bufferedImage = this.asSkiaBitmap().toBufferedImage()
    val baos = ByteArrayOutputStream()
    javax.imageio.ImageIO.write(bufferedImage, "png", baos)
    return baos.toByteArray()
}