package com.devscion.canvaspainter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devscion.canvaspainter.PainterController

@Composable
internal fun DialogColorPicker(controller: PainterController, onDismissed: () -> Unit) {
    Dialog(onDismissRequest = {
        onDismissed()
    }, properties = DialogProperties()) {
//        ClassicColorPicker(
//            showAlphaBar = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//        ) {
//            controller.setCustomColor(it.toColor())
//        }
    }
}