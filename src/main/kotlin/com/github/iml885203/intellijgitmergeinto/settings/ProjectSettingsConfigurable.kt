package com.github.iml885203.intellijgitmergeinto.settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent
import com.intellij.openapi.project.Project

class ProjectSettingsConfigurable(private val project: Project) : Configurable {

    private var mySettingsComponent: ProjectSettingsComponent? = null

    // A default constructor with no arguments is required because
    // this implementation is registered as an applicationConfigurable

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "SDK: Application Settings Example"
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return mySettingsComponent?.preferredFocusedComponent
    }

    override fun createComponent(): JComponent? {
        mySettingsComponent = ProjectSettingsComponent()
        return mySettingsComponent?.panel
    }

    override fun isModified(): Boolean {
        val state = ProjectSettings.getInstance(project).state
        return mySettingsComponent?.targetBranchText != state.targetBranch ||
                mySettingsComponent?.targetBranchOverwrite != state.targetBranchOverwrite
    }

    override fun apply() {
        val state = ProjectSettings.getInstance(project).state
        state.targetBranch = mySettingsComponent?.targetBranchText ?: ""
        state.targetBranchOverwrite = mySettingsComponent?.targetBranchOverwrite ?: false
    }

    override fun reset() {
        val state = ProjectSettings.getInstance(project).state
        mySettingsComponent?.targetBranchText = state.targetBranch
        mySettingsComponent?.targetBranchOverwrite = state.targetBranchOverwrite
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}
