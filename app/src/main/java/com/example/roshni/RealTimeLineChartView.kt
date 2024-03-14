package com.example.roshni

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class RealTimeLineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val dataPoints = mutableListOf<Float>()
    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        isAntiAlias = true
    }

    fun addDataPoint(value: Float) {
        dataPoints.add(value)
        invalidate() // Trigger redraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (dataPoints.size > 1) {
            for (i in 1 until dataPoints.size) {
                val x1 = (i - 1) * 10f // Adjust this value based on your x-axis scaling
                val y1 = height - dataPoints[i - 1]
                val x2 = i * 10f // Adjust this value based on your x-axis scaling
                val y2 = height - dataPoints[i]
                canvas.drawLine(x1, y1, x2, y2, paint)
            }
        }
    }
}
