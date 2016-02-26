package org.ligi.vectordrawableimporter

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.util.IconLoader

class IconsAction : AnAction("Vector Drawable Importer", "Imports vector drawables from Google\'s Material Icons", IconLoader.getIcon("/icons/android.png", IconsAction::class.java)) {

    override fun actionPerformed(event: AnActionEvent) {
        val project = AnAction.getEventProject(event)
        val module = event.getData(DataKeys.MODULE)

        val currentFacet = AndroidHelper.getCurrentFacet(project!!, module)

        if (currentFacet == null) {
            MessageHelper.message(project, "Android Facet not found to get res-paths", MessageType.ERROR)
        } else {
            val dialog = VectorImporter(project, module!!)
            dialog.show()
        }

    }
}
