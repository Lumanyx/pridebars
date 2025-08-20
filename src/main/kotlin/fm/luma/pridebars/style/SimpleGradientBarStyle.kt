package fm.luma.pridebars.style

import java.awt.Color
import java.awt.MultipleGradientPaint

class SimpleGradientBarStyle(colors: List<Color>) : IGradientBarStyle() {

    private val _colors: List<Color>

    init {
        val repeatedColorList = ArrayList<Color>()
        for (color in colors) {
            // Repeat every color a few times to make gradients between colors shorter
            repeat(3) {
                repeatedColorList.add(color)
            }
        }
        _colors = repeatedColorList.toList()
    }

    override fun getStyle(height: Int): MultipleGradientPaint {
        return makeGradient(_colors, height)
    }

}