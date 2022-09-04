package com.devscion.canvaspainter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devscion.canvaspainter.components.PenItem
import com.devscion.canvaspainter.models.PaintBrush
import com.devscion.canvaspainter.utils.AppUtils

@Composable
fun PensSection(
    modifier: Modifier = Modifier,
    selectedPos: MutableState<PaintBrush>
) {

    LazyRow(
        modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        items(items = AppUtils.PENS) { item ->
            val isSelected = (selectedPos.value.id == item.id)
            Box(
                Modifier.clickable {
                    selectedPos.value = item
                }
                    .padding(bottom = if (isSelected) 15.dp else 0.dp)
                    .background(
                        if (isSelected) Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                item.color.copy(alpha = 0.6f)
                            )
                        )
                        else Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent)),
                        CircleShape.copy(bottomStart = CornerSize(10.dp), bottomEnd = CornerSize(10.dp))
                    )
            ) {
                PenItem(
                    Modifier
                        .height(if (isSelected) 100.dp else 90.dp), item
                )
            }
        }
    }
}
