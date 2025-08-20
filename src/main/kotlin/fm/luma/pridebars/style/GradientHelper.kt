package fm.luma.pridebars.style

import java.awt.Color
import java.awt.LinearGradientPaint
import java.awt.MultipleGradientPaint

fun makeGradient(colors: List<Color>, height: Int): MultipleGradientPaint {
    val floats = FloatArray(colors.size)
    for (index in floats.indices) {
        floats[index] = index.toFloat() / (colors.size - 1)
    }
    return LinearGradientPaint(
        0f, 0F, 0f,
        height.toFloat(),
        floats,
        colors.toTypedArray()
    )
}