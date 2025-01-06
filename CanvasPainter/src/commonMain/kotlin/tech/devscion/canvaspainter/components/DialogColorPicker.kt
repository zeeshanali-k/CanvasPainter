package tech.devscion.canvaspainter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import tech.devscion.canvaspainter.PainterController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
internal fun DialogColorPicker(controller: PainterController, onDismissed: () -> Unit) {
    Dialog(onDismissRequest = {
        onDismissed()
    }, properties = DialogProperties()) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            onColorChanged = {
                controller.setCustomColor(it.color)
            },
            controller = rememberColorPickerController(),
        )
    }
}