package fm.luma.pridebars.ui

import com.intellij.util.ui.JBUI
import fm.luma.pridebars.config.PrideBarSettings
import fm.luma.pridebars.style.BarStyleRegistry
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.geom.RoundRectangle2D
import javax.swing.*
import javax.swing.plaf.ComponentUI
import javax.swing.plaf.basic.BasicProgressBarUI
import kotlin.math.PI
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
            ProgressBarTheme(style.getStyle(height), PrideBarSettings.getInstance().state.enableFade)
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

    override fun paintDeterminate(graphics: Graphics, component: JComponent) {
        if (graphics !is Graphics2D) return

        val bounds = getBounds(component)
        applyPaint(graphics, bounds.height)
        val config = getEffectiveConfig()

        var progressFactor = progressBar.value / 100.0
        if (progressBar.isIndeterminate) progressFactor = 1.0

        val visibleWidth = (progressFactor * bounds.width).toInt()
        if (bounds.width <= 0 || bounds.height <= 0) return

        var fadeOutWidth: Int = FADE_OUT_WIDTH
        if (config.fade) {
            if (progressFactor > FADE_OUT_END) {
                fadeOutWidth *= (progressFactor - FADE_OUT_END).toInt()
            }
        } else {
            fadeOutWidth = 0
        }

        setRenderHints(graphics)
        val fullRectangle = getRectangle(bounds, 1.0)

        val fadeOutOffset = if (config.fade) 4 else 0

        val filledProgress = (visibleWidth - (fadeOutWidth - fadeOutOffset)) / bounds.width.toDouble()
        val progressRectangle = getRectangle(bounds, filledProgress)
        graphics.color = determineBackground(component)
        graphics.fill(fullRectangle)

        applyPaint(graphics, bounds.height)
        if ((visibleWidth - fadeOutWidth) > 0)
            graphics.fill(progressRectangle)

        graphics.clip = fullRectangle
        for (fadeOutPixel in 0..<(fadeOutWidth * 2)) {
            val time = fadeOutPixel / fadeOutWidth.toFloat()
            var alpha = (time - 1.0)
            if (time < 1.0) {
                alpha = 1.0
            } else if (time >= 1.0) {
                alpha = 1.0 - (time - 1.0)
                alpha *= 0.5 + (sin(alpha * (PI / 2)))
            }
            if (alpha < MIN_FADE_ALPHA) continue

            graphics.composite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha.toFloat()
                    .coerceIn(0.0f, 1.0f)
            )
            graphics.fillRect(
                bounds.x + (visibleWidth - (fadeOutWidth * 2)) + fadeOutPixel + fadeOutOffset,
                bounds.y, 1, bounds.height
            )
        }
    }

    private fun getEffectiveConfig(): ProgressBarTheme {
        val forcedStyle = this._forcedStyle
        if (forcedStyle != null) return forcedStyle
        return ProgressBarTheme(
            _cachedPaint!!,
            PrideBarSettings.getInstance().state.enableFade,
        )
    }

    private fun determineBackground(component: JComponent): Color {
        return if (component.getParent() != null) component.getParent().getBackground() else Color.GRAY.darker()
    }

    override fun paintIndeterminate(graphics: Graphics, component: JComponent) {
        if (graphics !is Graphics2D) return
        val bounds = getBounds(component)
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        applyPaint(graphics, bounds.height)

        if (progressBar.getOrientation() != SwingConstants.HORIZONTAL
            || !component.getComponentOrientation().isLeftToRight
        ) {
            super.paintDeterminate(graphics, component)
            return
        }

        val b = progressBar.insets // area for border
        val barRectWidth = progressBar.getWidth() - (b.right + b.left)
        val barRectHeight = progressBar.getHeight() - (b.top + b.bottom)

        if (barRectWidth <= 0 || barRectHeight <= 0) return

        // Fill background
        val rectangle = getRectangle(bounds, 1.0)
        graphics.paint = determineBackground(component)
        graphics.fill(rectangle)

        applyPaint(graphics, bounds.height)
        graphics.fill(rectangle)

        val animPos = (System.currentTimeMillis() / 20L)
        val progress = 0.5 + (sin(animPos / 20.0) * 0.5)
        val multiplier = sin(progress * (Math.PI / 4)) * 2.0
        graphics.color = Color(0f, 0f, 0f, ((1.0 - progress) * multiplier).toFloat())
        graphics.fill(rectangle)
    }

    private fun getRectangle(bounds: Bounds, progress: Double): RoundRectangle2D.Double {
        return RoundRectangle2D.Double(
            bounds.x.toDouble(),
            bounds.y.toDouble(),
            bounds.width.toDouble() * progress.coerceIn(0.0, 1.0),
            bounds.height.toDouble(),
            8.0, 8.0
        )
    }

    override fun getPreferredSize(component: JComponent): Dimension {
        return Dimension(super.getPreferredSize(component).width, JBUI.scale(20))
    }

    override fun getBoxLength(availableLength: Int, otherDimension: Int): Int {
        return availableLength
    }

    companion object {
        const val FADE_OUT_END: Double = 0.95
        const val FADE_OUT_WIDTH: Int = 7
        const val MIN_FADE_ALPHA: Double = 0.1

        @JvmStatic
        @Suppress("ACCIDENTAL_OVERRIDE")
        fun createUI(c: JComponent): ComponentUI {
            c.border = JBUI.Borders.empty().asUIResource();
            return PrideBarTheme()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val frame = JFrame("Cute Progress Bar")
            frame.setSize(200, 200)
            val bar = PrideBarTheme()

            val bar1 = JProgressBar()
            bar1.setUI(bar)
            bar1.setSize(400, 100)
            bar1.value = 50

            val timer = Timer(5, ActionListener { e: ActionEvent? ->
                val `val` = (((sin((System.currentTimeMillis() / 800.0)) * 0.5) + 0.5) * 105.0)
                bar1.value = `val`.toInt()
            })
            timer.start()

            frame.setLocationRelativeTo(null)
            frame.add(bar1)
            frame.isVisible = true
        }

        private fun isEven(value: Int): Boolean {
            return value % 2 == 0
        }
    }
}
