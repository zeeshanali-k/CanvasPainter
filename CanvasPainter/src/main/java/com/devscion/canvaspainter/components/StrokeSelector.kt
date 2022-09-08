package com.devscion.canvaspainter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devscion.canvaspainter.PainterController
import com.devscion.canvaspainter.R

@Composable
fun StrokeSelector(painterController: PainterController) {

    val strokeWidth = painterController.strokeWidth.collectAsState()
    val selectedColor = painterController.selectedColor.collectAsState()

    Row(Modifier.fillMaxWidth()) {
        Slider(strokeWidth.value,
            colors = SliderDefaults.colors(
                thumbColor = selectedColor.value.color,
                activeTrackColor = selectedColor.value.color,
            ),
            valueRange = 5f..painterController.maxStrokeWidth,
            modifier = Modifier.weight(2f).padding(5.dp),
            onValueChange = {
                painterController.setStrokeWidth(it)
            })
        IconButton(onClick = {
            painterController.toggleStrokeSelection()
        }) {
            Icon(
                painterResource(R.drawable.ic_baseline_done_24),
                contentDescription = "done"
            )
        }
    }
}