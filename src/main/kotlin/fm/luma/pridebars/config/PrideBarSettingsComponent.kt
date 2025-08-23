@file:Suppress("JoinDeclarationAndAssignment")

package fm.luma.pridebars.config

import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import com.jetbrains.rd.swing.selectedItemProperty
import fm.luma.pridebars.style.BarStyleRegistry
import fm.luma.pridebars.ui.PrideBarTheme
import fm.luma.pridebars.ui.ProgressBarTheme
import fm.luma.pridebars.util.EasingMode
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Paint
import java.util.*
import javax.swing.*

class PrideBarSettingsComponent {

    fun parseSettings(): PrideBarSettings.ConfigState {
        val state = PrideBarSettings.ConfigState()
        state.style = styleDropdown.selectedItemProperty().value?.id ?: ""
        state.fadeWidth = fadeSlider.value
        state.roundingRadius = roundingRadius.value.toDouble()
        state.easing = (easingMode.selectedItem as EasingMode? ?: EasingMode.LINEAR).name
        return state
    }

    fun getPreviewTheme(style: String? = null): ProgressBarTheme {
        val paint: Paint = if (style != null) {
            BarStyleRegistry.getStyle(style).getStyle(JBUI.scale(20))
        } else {
            (styleDropdown.selectedItem as BarStyleRegistry.Entry).style.getStyle(JBUI.scale(20))
        }
        return ProgressBarTheme(
            paint,
            fadeSlider.value,
            easingMode.selectedItem as EasingMode? ?: EasingMode.LINEAR,
            roundingRadius.value.toDouble()
        )
    }

    fun applyState(state: PrideBarSettings.ConfigState) {
        val index = BarStyleRegistry.getStyleIndex(state.style)
        styleDropdown.selectedIndex = index
        fadeSlider.value = state.fadeWidth
        easingMode.selectedItem = state.easing
        roundingRadius.value = state.roundingRadius.toInt()

        previewPercentUI.setForcedStyle(getPreviewTheme())
        previewPercent.repaint()
        previewIndeterminateUI.setForcedStyle(getPreviewTheme())
        previewIndeterminate.repaint()
    }

    var panel: JPanel
    var styleDropdown: JComboBox<BarStyleRegistry.Entry>
    var easingMode: JComboBox<EasingMode>
    var fadeSlider: JSlider
    var roundingRadius: JSlider
    var previewSlider: JSlider
    var previewPercent: JProgressBar
    var previewIndeterminate: JProgressBar
    var previewPercentUI: PrideBarTheme
    var previewIndeterminateUI: PrideBarTheme

    init {
        styleDropdown = JComboBox(BarStyleRegistry.getStyles().toList().toTypedArray())
        decorateStyleDropdown()
        styleDropdown.addItemListener {
            val item = it.item
            if (item !is BarStyleRegistry.Entry) return@addItemListener
            updatePreviewBars()
        }

        easingMode = JComboBox(EasingMode.entries.toTypedArray())
        easingMode.addItemListener {
            updatePreviewBars()
        }

        roundingRadius = JSlider()
        roundingRadius.minimum = 0
        roundingRadius.maximum = 32
        roundingRadius.majorTickSpacing = 4
        roundingRadius.minorTickSpacing = 1
        roundingRadius.snapToTicks = true
        roundingRadius.paintTicks = true

        previewPercentUI = PrideBarTheme()
        previewPercent = JProgressBar()
        previewPercent.ui = previewPercentUI
        previewPercent.value = 50

        fadeSlider = JSlider()
        makeDictionary(fadeSlider)
        fadeSlider.minimum = 0
        fadeSlider.maximum = 32
        fadeSlider.snapToTicks = true
        fadeSlider.paintTicks = true
        fadeSlider.majorTickSpacing = 4
        fadeSlider.minorTickSpacing = 1

        previewSlider = JSlider()
        previewSlider.minimum = 0
        previewSlider.maximum = 100
        previewSlider.addChangeListener {
            previewPercent.value = previewSlider.value
        }

        previewIndeterminate = JProgressBar()
        previewIndeterminateUI = PrideBarTheme()
        previewIndeterminate.isIndeterminate = true
        previewIndeterminate.ui = previewIndeterminateUI

        fadeSlider.addChangeListener { updatePreviewBars() }
        roundingRadius.addChangeListener { updatePreviewBars() }

        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JLabel("Style"), styleDropdown)
            .addLabeledComponent(JLabel("Fade Strength"), fadeSlider)
            .addLabeledComponent(JLabel("Fade Easing"), easingMode)
            .addLabeledComponent(JLabel("Rounding Radius"), roundingRadius)
            .addLabeledComponent(
                JLabel("Preview (%)"), FormBuilder.createFormBuilder()
                    .addComponent(previewSlider).addComponent(previewPercent).panel
            )
            .addLabeledComponent(JLabel("Preview (indeterminate)"), previewIndeterminate)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun makeDictionary(fadeSlider: JSlider) {
        val dict = Hashtable<Int, JLabel>()
        for (value in fadeSlider.minimum until fadeSlider.maximum) {
            dict[value] = JLabel("$value px")
        }
        fadeSlider.labelTable = dict
    }

    private fun decorateStyleDropdown() {
        styleDropdown.renderer = object : ListCellRenderer<BarStyleRegistry.Entry> {
            override fun getListCellRendererComponent(
                list: JList<out BarStyleRegistry.Entry?>?,
                value: BarStyleRegistry.Entry?,
                index: Int,
                isSelected: Boolean,
                cellHasFocus: Boolean
            ): Component {
                if (value == null) return JLabel("")
                val panel = JPanel()
                val label = JLabel(value.name)

                val preview = JProgressBar()
                preview.value = 100
                preview.preferredSize = JBUI.size(20)

                val barUI = PrideBarTheme()
                barUI.setForcedStyle(getPreviewTheme(value.id))
                preview.ui = barUI

                panel.add(preview, BorderLayout.EAST)
                panel.add(label)

                if (isSelected) {
                    panel.setBackground(list!!.selectionBackground)
                    panel.setForeground(list.selectionForeground)
                } else {
                    panel.setBackground(list!!.getBackground())
                    panel.setForeground(list.getForeground())
                }

                return panel
            }
        }
    }

    private fun updatePreviewBars() {
        previewPercentUI.setForcedStyle(getPreviewTheme())
        previewIndeterminateUI.setForcedStyle(getPreviewTheme())
        previewPercent.repaint()
        previewIndeterminate.repaint()
    }

}