package tech.devscion.canvaspainter.utils

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.refTo
import kotlinx.cinterop.usePinned
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGImageAlphaInfo
import platform.Foundation.NSData
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy


@OptIn(ExperimentalForeignApi::class)
actual fun ImageBitmap.toByteArray(): ByteArray? {
    val uiImage = this.toUIImage() // Convert ImageBitmap to UIImage
    val nsData: NSData? =
        uiImage?.let { UIImagePNGRepresentation(it) } // Get PNG data (use UIImageJPEGRepresentation for JPEG)

    return nsData?.let { data ->
        val length = data.length.toInt()
        val byteArray = ByteArray(length)

        data.bytes?.let { pointer ->
            byteArray.usePinned { pinnedArray ->
                memcpy(pinnedArray.addressOf(0), pointer, length.convert())
            }
        }

        byteArray
    }
}


@OptIn(ExperimentalForeignApi::class)
fun ImageBitmap.toUIImage(): UIImage? {
    val width = this.width
    val height = this.height
    val buffer = IntArray(width * height)

    this.readPixels(buffer)

    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val context = CGBitmapContextCreate(
        data = buffer.refTo(0),
        width = width.toULong(),
        height = height.toULong(),
        bitsPerComponent = 8u,
        bytesPerRow = (4 * width).toULong(),
        space = colorSpace,
        bitmapInfo = CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value
    )

    val cgImage = CGBitmapContextCreateImage(context)
    return cgImage?.let { UIImage.imageWithCGImage(it) }
}