package org.ligi.vectordrawableimporter

import com.intellij.facet.ProjectFacetManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import org.jetbrains.android.facet.AndroidFacet

object AndroidHelper {

    fun getCurrentFacet(project: Project, module: Module?): AndroidFacet? {
        if (module == null) {
            return null
        }
        val applicationFacets = ProjectFacetManager.getInstance(project).getFacets(AndroidFacet.ID)
        return applicationFacets.find { it.module.name.equals(module.name) }
    }
}
