package fm.luma.pridebars.ui

import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import fm.luma.pridebars.config.PrideBarSettings
import fm.luma.pridebars.style.BarStyleRegistry
import fm.luma.pridebars.util.EasingMode
import fm.luma.pridebars.util.getEasing
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D
import java.awt.image.BufferedImage
import javax.swing.JComponent
import javax.swing.JProgressBar
import javax.swing.SwingConstants
import javax.swing.plaf.ComponentUI
import javax.swing.plaf.basic.BasicProgressBarUI
import kotlin.math.sin


class PrideBarTheme : BasicProgressBarUI() {

    override fun installListeners() {
        super.installListeners()
        progressBar.addComponentListener(object : ComponentAdapter() {
            override fun componentShown(e: ComponentEvent?) {
                super.componentShown(e)
            }

            override fun componentHidden(e: ComponentEvent?) {
                super.componentHidden(e)
            }
        })
    }

    private var _forcedStyle: ProgressBarTheme? = null
    fun setForcedStyle(forcedStyle: ProgressBarTheme) {
        _forcedStyle = forcedStyle
        _cachedPaint = null
    }

    private var _cachedPaint: Paint? = null
    private var _lastStyle: ProgressBarTheme? = null

    private fun shouldRebuildPaintCache(): Boolean {
        if (_forcedStyle != null) return false
        return (_lastStyle != BarStyleRegistry.getStyle(PrideBarSettings.getInstance().state.style))
    }

    private fun applyPaint(g2d: Graphics2D, height: Int) {
        if (_cachedPaint != null && !shouldRebuildPaintCache()) {
            g2d.paint = _cachedPaint
            return
        }
        val style = if (_forcedStyle != null) {
            _forcedStyle
        } else {
            val style = BarStyleRegistry.getStyle(PrideBarSettings.getInstance().state.style)
            ProgressBarTheme(
                style.getStyle(height),
                PrideBarSettings.getInstance().state.fadeWidth,
                getEasing(PrideBarSettings.getInstance().state.easing),
                PrideBarSettings.getInstance().state.roundingRadius,
            )
        }
        _lastStyle = style
        _cachedPaint = style!!.gradient
        g2d.paint = _cachedPaint
    }

    private data class Bounds(val x: Int, val y: Int, val width: Int, val height: Int)

    private fun getBounds(component: Component): Bounds {
        val w = progressBar.getWidth()
        var h = progressBar.preferredSize.height
        if (!isEven(component.getHeight() - h)) h++
        return Bounds(0, 0, w, h)
    }

    override fun paint(g: Graphics, c: JComponent?) {
        val bar = c as JProgressBar
        if (bar.isIndeterminate) {
            bar.invalidate()
            paintIndeterminate(g, c)
            return
        }
        super.paint(g, c)
    }

    fun setRenderHints(graphics: Graphics2D) {
        val qualityHints = RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )
        qualityHints[RenderingHints.KEY_RENDERING] = RenderingHints.VALUE_RENDER_QUALITY
        graphics.setRenderingHints(qualityHints)
    }

    fun generateProgressBar(width: Int, height: Int, time: Float, fadeWidth: Int, fadingMode: EasingMode): BufferedImage {
        val image = UIUtil.createImage(null, width, height, BufferedImage.TYPE_INT_ARGB)
        val g2d = image.createGraphics()
        setRenderHints(g2d)
        applyPaint(g2d, height)

        val occupiedPixels = (width.toFloat() * time).toInt().coerceIn(0, width)
        val fadePixels = determineFadePixels(JBUI.scale(fadeWidth), time)
        val fullOpacityWidth = occupiedPixels - fadePixels
        g2d.fillRect(0, 0, fullOpacityWidth, height)
        for (pixel in 0..<fadePixels) {
            val alpha = fadingMode.ease(1.0 - (pixel.toFloat() / fadePixels.toFloat())).coerceIn(0.0, 1.0)
            if (alpha < MIN_FADE_ALPHA) continue

            g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha.toFloat())
            g2d.fillRect(fullOpacityWidth + pixel, 0, 1, height)
        }
        return image
    }

    private fun determineFadePixels(fadeWidth: Int, time: Float): Int {
        if (time <= FADE_OUT_END) return fadeWidth
        if (time >= 1.0) return 0
        val fadeOutLength = 1.0 - FADE_OUT_END
        val relativeTime = (time - FADE_OUT_END)
        val factor = 1.0 - (relativeTime / fadeOutLength)
        return (fadeWidth * factor).toInt()
    }

    override fun paintDeterminate(graphics: Graphics, component: JComponent) {
        if (graphics !is Graphics2D) return

        val bounds = getBounds(component)
        applyPaint(graphics, bounds.height)
        setRenderHints(graphics)
        val config = getEffectiveConfig()

        var progressFactor = (progressBar.value / 100.0).coerceIn(0.0, 1.0)
        if (progressBar.isIndeterminate) progressFactor = 1.0

        val fullRectangle = getRectangle(bounds, 1.0, config.roundingRadius)
        val texture = generateProgressBar(
            bounds.width, bounds.height,
            progressFactor.toFloat(),
            config.fadePixels,
            config.easing
        )

        graphics.clearRect(bounds.x, bounds.y, bounds.width, bounds.height)
        graphics.paint = determineBackground(component)
        graphics.fillRect(0, 0, bounds.width, bounds.height)
        graphics.paint = TexturePaint(
            texture,
            Rectangle2D.Float(
                0.0f, 0.0f,
                bounds.width.toFloat(), bounds.height.toFloat()
            )
        )
        graphics.fill(fullRectangle)
    }

    private fun getEffectiveConfig(): ProgressBarTheme {
        val forcedStyle = this._forcedStyle
        if (forcedStyle != null) return forcedStyle
        return ProgressBarTheme(
            _cachedPaint!!,
            PrideBarSettings.getInstance().state.fadeWidth,
            getEasing(PrideBarSettings.getInstance().state.easing),
            PrideBarSettings.getInstance().state.roundingRadius,
        )
    }

    private fun getProgressFactor(): Double {
        var progressFactor = (progressBar.value / 100.0).coerceIn(0.0, 1.0)
        if (progressBar.isIndeterminate) progressFactor = 1.0
        return progressFactor
    }

    override fun paintIndeterminate(graphics: Graphics, component: JComponent) {
        if (graphics !is Graphics2D) return
        val bounds = getBounds(component)
        val config = getEffectiveConfig()

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)

        applyPaint(graphics, bounds.height)

        if (progressBar.getOrientation() != SwingConstants.HORIZONTAL
            || !component.getComponentOrientation().isLeftToRight
        ) {
            super.paintDeterminate(graphics, component)
            return
        }

        // Fill background
        val progressFactor = getProgressFactor()
        val fullRectangle = getRectangle(bounds, 1.0, config.roundingRadius)
        graphics.paint = determineBackground(component)
        graphics.fill(fullRectangle)

        val texture = generateProgressBar(
            bounds.width, bounds.height,
            progressFactor.toFloat(),
            config.fadePixels,
            config.easing
        )
        
        graphics.clearRect(bounds.x, bounds.y, bounds.width, bounds.height)
        graphics.paint = determineBackground(component)
        graphics.fill(fullRectangle)

        graphics.paint = TexturePaint(
            texture,
            Rectangle2D.Float(
                0.0f, 0.0f,
                bounds.width.toFloat(), bounds.height.toFloat()
            )
        )
        graphics.fill(fullRectangle)

        val animPos = (System.currentTimeMillis() / 20L)
        val progress = 0.5 + (sin(animPos / 20.0) * 0.5)
        val multiplier = sin(progress * (Math.PI / 4)) * 2.0
        graphics.color = Color(0f, 0f, 0f, ((1.0 - progress) * multiplier).toFloat())
        graphics.fill(fullRectangle)
    }

    private fun getRectangle(bounds: Bounds, progress: Double, rounding: Double): RoundRectangle2D.Double {
        return RoundRectangle2D.Double(
            bounds.x.toDouble(),
            bounds.y.toDouble(),
            bounds.width.toDouble() * progress.coerceIn(0.0, 1.0),
            bounds.height.toDouble(),
            JBUI.scale(rounding.toInt()).toDouble(),
            JBUI.scale(rounding.toInt()).toDouble()
        )
    }

    override fun getPreferredSize(component: JComponent): Dimension {
        return Dimension(super.getPreferredSize(component).width, JBUI.scale(20))
    }

    override fun getBoxLength(availableLength: Int, otherDimension: Int): Int {
        return availableLength
    }

    companion object {
        const val FADE_OUT_END: Double = 0.7
        const val MIN_FADE_ALPHA: Double = 0.05

        @JvmStatic
        @Suppress("ACCIDENTAL_OVERRIDE")
        fun createUI(c: JComponent): ComponentUI {
            c.border = JBUI.Borders.empty().asUIResource()
            return PrideBarTheme()
        }

        private fun isEven(value: Int): Boolean {
            return value % 2 == 0
        }
    }

    private fun determineBackground(component: JComponent): Color {
        return if (component.getParent() != null) component.getParent().getBackground() else Color.GRAY.darker()
    }

}
