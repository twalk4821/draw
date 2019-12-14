package com.reactlibrary

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View

import com.facebook.react.uimanager.ThemedReactContext

class CanvasViewInternal(private val _context: ThemedReactContext) : View(_context) {
    private val paint = Paint()
    private val basePaint = Paint()

    private var size = 200f
    private val baseSize = 200f
    private var _canvas: Canvas? = null

    init {
        paint.strokeWidth = 10f
        paint.color = Color.RED

        basePaint.strokeWidth = 10f
        basePaint.color = Color.WHITE
    }

    fun onTap() {
        size = size - 1f
        draw(_canvas)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        _canvas = canvas
        canvas.drawRect(0f, 0f, baseSize, baseSize, basePaint)
        canvas.drawRect(0f, 0f, size, size, paint)
    }

    override fun getMinimumWidth(): Int {
        return 200
    }

    override fun getMinimumHeight(): Int {
        return 200
    }
}
