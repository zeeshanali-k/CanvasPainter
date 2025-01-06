package tech.devscion.canvaspainter.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import canvaspainterroot.canvaspainter.generated.resources.Res
import canvaspainterroot.canvaspainter.generated.resources.ic_baseline_adjust_24
import canvaspainterroot.canvaspainter.generated.resources.ic_baseline_brush_24
import canvaspainterroot.canvaspainter.generated.resources.ic_baseline_clear_all_24
import canvaspainterroot.canvaspainter.generated.resources.ic_round_redo_24
import canvaspainterroot.canvaspainter.generated.resources.ic_round_undo_24
import tech.devscion.canvaspainter.PainterController
import tech.devscion.canvaspainter.PensSection
import org.jetbrains.compose.resources.painterResource

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
            .scrollable(
                enabled = isColorSelection.value,
                orientation = Orientation.Vertical,
                // Scrollable state: describes how to consume
                // scrolling delta and update offset
                state = rememberScrollableState { delta ->
                    if (delta < -20) {
                        isColorSelection.value = false
                    }
                    delta
                }
            )
    ) {
        if (isColorSelection.value)
            PensSection(painterController = painterController)
        Row(
            modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (isStrokeSelection.value) {
                StrokeSelector(
                    minStrokeWidth = painterController.minStrokeWidth,
                    maxStrokeWidth = painterController.maxStrokeWidth,
                    strokeWidth = painterController.strokeWidth.collectAsState().value,
                    selectedColor = painterController.selectedColor.collectAsState().value,
                    onStrokeWidthUpdate = painterController::setStrokeWidth,
                    toggleStrokeSelection = painterController::toggleStrokeSelection,
                )
            } else {
                Row {
//                    Stroke Selection
                    IconButton(onClick = {
                        painterController.toggleStrokeSelection()
                    }) {
                        Icon(painterResource(Res.drawable.ic_baseline_adjust_24), "stroke")
                    }

//                    Color Selection
                    IconButton(onClick = {
                        isColorSelection.value = isColorSelection.value.not()
                    }) {
                        Image(
                            painterResource(Res.drawable.ic_baseline_brush_24),
                            "color",
                            colorFilter = ColorFilter.tint(selectedColor.value.color)
                        )
                    }
//                    Undo
                    IconButton(
                        onClick = {
                            painterController.undo()
                        },
                        enabled = paintPath.value.isNotEmpty(),
                    ) {
                        Icon(painterResource(Res.drawable.ic_round_undo_24), "undo")
                    }
//                    Redo
                    IconButton(
                        onClick = {
                            painterController.redo()
                        },
                        enabled = undonePath.value.isNotEmpty(),
                    ) {
                        Icon(painterResource(Res.drawable.ic_round_redo_24), "redo")
                    }

//                    Redo
                    IconButton(
                        onClick = {
                            painterController.reset()
                        },
                        enabled = paintPath.value.isNotEmpty(),
                    ) {
                        Icon(painterResource(Res.drawable.ic_baseline_clear_all_24), "redo")
                    }
                }
            }
        }
    }

}