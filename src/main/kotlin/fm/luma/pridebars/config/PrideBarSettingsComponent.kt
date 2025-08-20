@file:Suppress("JoinDeclarationAndAssignment")

package fm.luma.pridebars.config

import com.intellij.ui.layout.selected
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import com.jetbrains.rd.swing.selectedItemProperty
import fm.luma.pridebars.style.BarStyleRegistry
import fm.luma.pridebars.ui.PrideBarTheme
import fm.luma.pridebars.ui.ProgressBarTheme
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Paint
import javax.swing.*

class PrideBarSettingsComponent {

    fun parseSettings(): PrideBarSettings.ConfigState {
        val state = PrideBarSettings.ConfigState()
        state.style = styleDropdown.selectedItemProperty().value?.id ?: ""
        state.enableFade = enableFadeBox.isSelected
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
            enableFadeBox.isSelected
        )
    }

    fun applyState(state: PrideBarSettings.ConfigState) {
        val index = BarStyleRegistry.getStyleIndex(state.style)
        styleDropdown.selectedIndex = index
        enableFadeBox.isSelected = state.enableFade

        previewPercentUI.setForcedStyle(getPreviewTheme())
        previewPercent.repaint()
        previewIndeterminateUI.setForcedStyle(getPreviewTheme())
        previewIndeterminate.repaint()
    }

    var panel: JPanel
    var styleDropdown: JComboBox<BarStyleRegistry.Entry>
    var enableFadeBox: JCheckBox
    var previewSlider: JSlider
    var previewPercent: JProgressBar
    var previewIndeterminate: JProgressBar
    var previewPercentUI: PrideBarTheme
    var previewIndeterminateUI: PrideBarTheme

    init {
        styleDropdown = JComboBox(BarStyleRegistry.getStyles().toList().toTypedArray())
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
        styleDropdown.addItemListener {
            val item = it.item
            if (item !is BarStyleRegistry.Entry) return@addItemListener
            updatePreviewBars()
        }

        previewPercentUI = PrideBarTheme()
        previewPercent = JProgressBar()
        previewPercent.ui = previewPercentUI
        previewPercent.value = 50

        enableFadeBox = JCheckBox()
        enableFadeBox.selected.addListener { updatePreviewBars() }
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

        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JLabel("Style"), styleDropdown)
            .addLabeledComponent(JLabel("Enable Fade"), enableFadeBox)
            .addLabeledComponent(
                JLabel("Preview (%)"), FormBuilder.createFormBuilder()
                    .addComponent(previewSlider).addComponent(previewPercent).panel
            )
            .addLabeledComponent(JLabel("Preview (indeterminate)"), previewIndeterminate)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun updatePreviewBars() {
        previewPercentUI.setForcedStyle(getPreviewTheme())
        previewIndeterminateUI.setForcedStyle(getPreviewTheme())
        previewPercent.repaint()
        previewIndeterminate.repaint()
    }

}