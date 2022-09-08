package com.devscion.canvaspainter.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devscion.canvaspainter.PainterController
import com.devscion.canvaspainter.PensSection
import com.devscion.canvaspainter.R

@Composable
internal fun PainterToolbar(
    modifier: Modifier = Modifier,
    painterController: PainterController
) {

    val isColorSelection = remember {
        mutableStateOf(false)
    }

    val isStrokeSelection = painterController.isStrokeSelection.collectAsState()
    val selectedColor = painterController.selectedColor.collectAsState()
    val undonePath = painterController.undonePath.collectAsState()
    val paintPath = painterController.paintPath.collectAsState()

    Column(
        Modifier.fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.5f))
            .animateContentSize()
    ) {

        if (isColorSelection.value)
            PensSection(painterController = painterController)
        Row(
            modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isStrokeSelection.value) {
                StrokeSelector(painterController)
            } else {
                Row {
                    IconButton(onClick = {
                        painterController.toggleStrokeSelection()
                    }) {
                        Icon(painterResource(R.drawable.ic_baseline_adjust_24), "stroke")
                    }
                    IconButton(onClick = {
                        isColorSelection.value = isColorSelection.value.not()
                    }) {
                        Image(
                            painterResource(R.drawable.ic_baseline_brush_24),
                            "color",
                            colorFilter = ColorFilter.tint(selectedColor.value.color)
                        )
                    }
                    IconButton(
                        onClick = {
                            painterController.undo()
                        },
                        enabled = paintPath.value.isNotEmpty(),
                    ) {
                        Icon(painterResource(R.drawable.ic_round_undo_24), "undo")
                    }
                    IconButton(
                        onClick = {
                            painterController.redo()
                        },
                        enabled = undonePath.value.isNotEmpty(),
                    ) {
                        Icon(painterResource(R.drawable.ic_round_redo_24), "redo")
                    }
                }
                IconButton(onClick = {
                    painterController.saveBitmap()
                }) {
                    Icon(painterResource(R.drawable.ic_baseline_save_24), "save")
                }
            }


        }
    }

}