package com.devscion.canvaspainter.models

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.devscion.canvaspainter.utils.AppUtils

data class PaintPath(
//    val path: Path,
    val points : SnapshotStateList<Offset>,
    val color: Color
)