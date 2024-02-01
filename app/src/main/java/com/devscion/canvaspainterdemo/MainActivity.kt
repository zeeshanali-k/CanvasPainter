package com.devscion.canvaspainterdemo

import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.devscion.canvaspainter.CanvasPainter
import com.devscion.canvaspainter.models.StorageOptions
import com.devscion.canvaspainter.rememberCanvasPainterController
import com.devscion.canvaspainterdemo.ui.theme.CanvasPainterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it.not()) {
                showPermissionToast()
            }
        }
        if (SDK_INT >= O && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showPermissionToast()
            } else {
                permLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        setContent {
            val painterController = rememberCanvasPainterController(
                maxStrokeWidth = 200f,
                showToolbar = true,
                storageOptions = StorageOptions(shouldSaveByDefault = true),
                Color.Red
            )
            CanvasPainterTheme {
                CanvasPainter(
                    Modifier.fillMaxSize()
                        .background(Color.Black),
                    painterController
                )
            }
        }
    }

    private fun showPermissionToast() {
        Toast.makeText(
            applicationContext, "Please Grant Storage Permission in order to save Painting",
            Toast.LENGTH_LONG
        )
            .show()
    }

}
