package com.reactlibrary.extensions

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import com.reactlibrary.types.Drop
import com.reactlibrary.types.Path

val basePaint = Paint().apply {
    color = Color.WHITE
}

fun Canvas.clear(width: Float, height: Float) {
    drawRect(0F, 0F, width, height, basePaint)
}

fun Canvas.drawPath(path: Path, paint: Paint) {
    path.forEachIndexed { index, pointF ->
        if (index > 0) {
            val start = path[index - 1]
            drawLine(start.x, start.y, pointF.x, pointF.y, paint)
        }
    }
}

fun Canvas.drawDrop(drop: Drop, paint: Paint) {
    drop.apply {
        drawCircle(cx, cy, radius, paint)
    }
}