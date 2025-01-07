package tech.devscion.canvaspainter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import canvaspainterroot.canvaspainter.generated.resources.Res
import canvaspainterroot.canvaspainter.generated.resources.ic_baseline_done_24
import org.jetbrains.compose.resources.painterResource
import tech.devscion.canvaspainter.models.PaintBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrokeSelector(
    minStrokeWidth: Float,
    maxStrokeWidth: Float,
    strokeWidth: Float,
    selectedColor: PaintBrush,
    onStrokeWidthUpdate: (Float) -> Unit,
    toggleStrokeSelection: () -> Unit,
) {

    Row(Modifier.fillMaxWidth()) {
        Slider(
            strokeWidth,
            colors = SliderDefaults.colors(
                activeTrackColor = selectedColor.color,
            ),
            thumb = {
                Spacer(
                    Modifier.size(18.dp)
                        .clip(CircleShape)
                        .background(selectedColor.color)
                )
            },
            valueRange = minStrokeWidth..maxStrokeWidth,
            modifier = Modifier.weight(2f).padding(5.dp),
            onValueChange = onStrokeWidthUpdate,
        )
        IconButton(onClick = toggleStrokeSelection) {
            Icon(
                painterResource(Res.drawable.ic_baseline_done_24),
                contentDescription = "done"
            )
        }
    }
}