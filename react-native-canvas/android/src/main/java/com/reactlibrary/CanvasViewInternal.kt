package com.reactlibrary

import android.graphics.*
import android.view.View
import androidx.core.os.HandlerCompat.postDelayed
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.soloader.SoLoader.init
import com.reactlibrary.extensions.basePaint
import com.reactlibrary.extensions.clear
import com.reactlibrary.extensions.drawDrop
import com.reactlibrary.extensions.drawPath
import com.reactlibrary.types.Drop
import com.reactlibrary.types.Path


class CanvasViewInternal(_context: ThemedReactContext) : View(_context) {
    private val paint = Paint()
    private var _canvas: Canvas? = null

    private var currentPath: Path = mutableListOf()
    private var isPathDissolving = false

    private val drops = mutableListOf<Drop>()
    private val dropMaxRadius = 35F
    private val dropRadiusIncrement = 1F

    private val fps = 45L

    init {
        paint.strokeWidth = 10f
        paint.color = Color.RED
    }

    fun startPath() {
        currentPath = mutableListOf()
        isPathDissolving = false
        isDrawScheduled = false
    }

    fun addPoint(x: Float, y: Float) {
        currentPath.add(PointF(x, y))
        if (!isDrawScheduled) {
            draw(_canvas)
            invalidateOutline()
        }
    }

    fun endPath() {
        isPathDissolving = true
        if (!isDrawScheduled) {
            draw(_canvas)
            invalidateOutline()
        }
    }

    fun createDrop(x: Float, y: Float) {
        drops.add(Drop(x, y, 0F))

        // limit drawn drops to 3 for performance reasons
        if (drops.size > 3) {
            drops.removeAt(0)
        }

        if (!isDrawScheduled) {
            draw(_canvas)
            invalidateOutline()
        }
    }

    private fun updatePath() {
        if (currentPath.size > 0) {
            currentPath.removeAt(0)
        }
    }

    private fun updateDrops() {
        val toRemove = mutableListOf<Drop>()
        drops.forEach {
            when {
                it.radius >= 0 && it.radius < dropMaxRadius && !it.isWaning -> {
                    // waxing
                    it.radius += dropRadiusIncrement
                }
                it.radius >= dropMaxRadius -> {
                    // reached max size
                    it.isWaning = true
                    it.radius -= dropRadiusIncrement
                }
                it.radius >= 0 && it.radius < dropMaxRadius && it.isWaning -> {
                    // waning
                    it.radius -= dropRadiusIncrement
                }
                else -> {
                    // finished animating
                    toRemove.add(it)
                }
            }
        }

        toRemove.forEach { drops.remove(it) }
    }

    var isDrawScheduled = false
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        _canvas = canvas

        canvas.clear(width.toFloat(), height.toFloat())

        drawCurrentPath(canvas)
        drawDrops(canvas)
    }

    private fun drawCurrentPath(canvas: Canvas) {
        canvas.drawPath(currentPath, paint)

        if (isPathDissolving) {
            // dissolve first point from path
            updatePath()

            if (currentPath.size > 0) {
                // schedule next frame
                scheduleDraw()
            } else {
                isPathDissolving = false
            }
        }
    }

    private fun drawDrops(canvas: Canvas) {
        drops.forEach {
            canvas.drawDrop(it, paint)
        }

        // update drops to sizes for next frame
        updateDrops()
        if (drops.size > 0) {
            // schedule next frame
            scheduleDraw()
        }
    }

    private fun scheduleDraw() {
        isDrawScheduled = true
        postDelayed({
            isDrawScheduled = false
            draw(_canvas)
            invalidateOutline()
        }, 1000L/fps)
    }
}
