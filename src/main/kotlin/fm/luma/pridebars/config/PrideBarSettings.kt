package fm.luma.pridebars.config

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


@State(
    name = "fm.luma.pridebars.config.PrideBarSettings",
    storages = [Storage("PrideBarSettings.xml")]
)
class PrideBarSettings : PersistentStateComponent<PrideBarSettings.ConfigState> {

    class ConfigState {
        var style: String = "pride"
        var enableFade: Boolean = true

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ConfigState

            if (enableFade != other.enableFade) return false
            if (style != other.style) return false

            return true
        }

        override fun hashCode(): Int {
            var result = enableFade.hashCode()
            result = 31 * result + style.hashCode()
            return result
        }


    }

    private var state: ConfigState = ConfigState()

    override fun getState(): ConfigState {
        return state
    }

    override fun loadState(p0: ConfigState) {
        this.state = p0
    }

    companion object {
        @JvmStatic
        fun getInstance(): PrideBarSettings {
            return ApplicationManager.getApplication()
                .getService(PrideBarSettings::class.java)
        }
    }

}