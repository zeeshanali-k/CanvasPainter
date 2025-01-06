package tech.devscion.canvaspainter.models

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

internal data class PaintPath(
    val points: SnapshotStateList<Offset>,
    val stroke: Float,
    val color: Color
)