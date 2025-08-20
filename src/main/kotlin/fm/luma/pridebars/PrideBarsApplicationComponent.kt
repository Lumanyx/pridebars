package fm.luma.pridebars

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.wm.IdeFrame
import fm.luma.pridebars.ui.PrideBarTheme
import javax.swing.UIManager

class PrideBarsApplicationComponent() : LafManagerListener, ApplicationActivationListener {

    override fun lookAndFeelChanged(p0: LafManager) {
        update()
    }

    override fun applicationActivated(ideFrame: IdeFrame) {
        update()
    }

    companion object {
        fun update() {
            UIManager.put("ProgressBarUI", PrideBarTheme::class.java.name)
            UIManager.getDefaults()[PrideBarTheme::class.java.name] = PrideBarTheme::class.java
        }
    }

}