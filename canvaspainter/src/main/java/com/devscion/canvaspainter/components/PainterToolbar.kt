package com.devscion.canvaspainter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devscion.canvaspainter.PainterController
import com.devscion.canvaspainter.R

@Composable
fun PainterToolbar(
    modifier: Modifier = Modifier,
    painterController: PainterController
) {

    val isStrokeSelection = remember {
        mutableStateOf(false)
    }

    Row(
        modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.5f)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isStrokeSelection.value) {
            LinearProgressIndicator(25f, Modifier.fillMaxWidth().padding(5.dp))
        } else {
            IconButton(onClick = {
                isStrokeSelection.value = isStrokeSelection.value.not()
            }) {
                Icon(painterResource(R.drawable.ic_baseline_adjust_24), "stroke")
            }
        }

        IconButton(onClick = {
            painterController.saveBitmap()
        }){
            Icon(painterResource(R.drawable.ic_baseline_save_24), "save")
        }
    }

}