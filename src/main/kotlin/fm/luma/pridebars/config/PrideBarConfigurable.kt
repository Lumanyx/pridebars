package fm.luma.pridebars.config

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.util.NlsContexts
import javax.swing.JComponent

class PrideBarConfigurable : Configurable {

    override fun getDisplayName(): @NlsContexts.ConfigurableName String {
        return "PrideBars"
    }

    private var _component: PrideBarSettingsComponent? = null

    override fun createComponent(): JComponent {
        val component = PrideBarSettingsComponent()
        _component = component
        return component.panel
    }

    override fun isModified(): Boolean {
        val state = PrideBarSettings.getInstance().state
        val component = _component ?: return false

        val updatedConfig = component.parseSettings()
        return updatedConfig != state
    }

    override fun disposeUIResources() {
        _component = null
    }

    override fun reset() {
        val component = _component ?: return
        component.applyState(PrideBarSettings.getInstance().state)
    }

    override fun apply() {
        val component = _component ?: return
        val state = PrideBarSettings.getInstance().state
        val parsedState = component.parseSettings()
        state.style = parsedState.style
        state.fadeWidth = parsedState.fadeWidth
    }
}