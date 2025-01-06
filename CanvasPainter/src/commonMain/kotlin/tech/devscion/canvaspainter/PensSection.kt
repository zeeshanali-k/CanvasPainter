package tech.devscion.canvaspainter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tech.devscion.canvaspainter.components.DialogColorPicker
import tech.devscion.canvaspainter.components.PenItem
import tech.devscion.canvaspainter.utils.AppUtils

@Composable
internal fun PensSection(
    modifier: Modifier = Modifier,
    painterController: PainterController
) {

    val selectedColor = painterController.selectedColor.collectAsState()
    val isPickerMode = remember {
        mutableStateOf(false)
    }

    Box {
        if (isPickerMode.value) {
            DialogColorPicker(painterController) {
                isPickerMode.value = false
            }
        }
        LazyRow(
            modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {
            items(items = AppUtils.PENS) { item ->
                val isSelected = (selectedColor.value.id == item.id)
                Box(
                    Modifier.clickable {
                        if (item.id == -1) {
                            isPickerMode.value = true
                        } else {
                            painterController.setPaintColor(item)
                        }
                    }
                        .padding(bottom = if (isSelected) 15.dp else 0.dp)
                        .background(
                            if (isSelected) Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    item.color.copy(alpha = 0.6f)
                                )
                            )
                            else Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Transparent
                                )
                            ),
                            CircleShape.copy(
                                bottomStart = CornerSize(10.dp),
                                bottomEnd = CornerSize(10.dp)
                            )
                        )
                ) {
                    PenItem(
                        Modifier
                            .height(if (isSelected) 100.dp else 90.dp),
                        item,
                    )
                }
            }
        }
    }
}

