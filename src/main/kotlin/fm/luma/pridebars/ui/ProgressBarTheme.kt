package fm.luma.pridebars.ui

import fm.luma.pridebars.util.EasingMode
import java.awt.Paint

data class ProgressBarTheme(
    val gradient: Paint,
    val fadePixels: Int,
    val easing: EasingMode,
    val roundingRadius: Double
)