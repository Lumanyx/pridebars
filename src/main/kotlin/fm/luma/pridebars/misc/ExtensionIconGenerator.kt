package fm.luma.pridebars.misc

import com.intellij.util.ui.JBUI
import fm.luma.pridebars.style.PRIDE_FLAG_STYLE

fun makeIconSvg() {
    val colors = PRIDE_FLAG_STYLE.getStyle(JBUI.scale(20)).colors
    for (index in colors.indices) {
        val offset = (index / colors.size.toFloat())
        val reversedIndex = (colors.size - 1) - index
        val color = colors[reversedIndex]
        val hexColor = String.format("#%02x%02x%02x", color.red, color.green, color.blue);
        println("<stop offset=\"$offset\" stop-color=\"$hexColor\"/>")
    }
}