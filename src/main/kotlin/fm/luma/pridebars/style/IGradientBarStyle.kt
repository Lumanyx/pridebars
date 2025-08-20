package fm.luma.pridebars.style

import java.awt.MultipleGradientPaint

abstract class IGradientBarStyle {

    abstract fun getStyle(height: Int): MultipleGradientPaint

}