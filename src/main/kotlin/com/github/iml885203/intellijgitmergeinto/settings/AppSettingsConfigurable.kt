package com.github.iml885203.intellijgitmergeinto.settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class AppSettingsConfigurable : Configurable {

    private var mySettingsComponent: AppSettingsComponent? = null

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
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent?.panel
    }

    override fun isModified(): Boolean {
        val state = AppSettings.instance.state
        return mySettingsComponent?.targetBranchText != state.targetBranch ||
                mySettingsComponent?.pushAfterMerge != state.pushAfterMerge ||
                mySettingsComponent?.runInBackground != state.runInBackground ||
                mySettingsComponent?.abortMergeWhenConflicts != state.abortMergeWhenConflicts
    }

    override fun apply() {
        val state = AppSettings.instance.state
        state.targetBranch = mySettingsComponent?.targetBranchText ?: ""
        state.pushAfterMerge = mySettingsComponent?.pushAfterMerge ?: false
        state.runInBackground = mySettingsComponent?.runInBackground ?: false
        state.abortMergeWhenConflicts = mySettingsComponent?.abortMergeWhenConflicts ?: false
    }

    override fun reset() {
        val state = AppSettings.instance.state
        mySettingsComponent?.targetBranchText = state.targetBranch
        mySettingsComponent?.pushAfterMerge = state.pushAfterMerge
        mySettingsComponent?.runInBackground = state.runInBackground
        mySettingsComponent?.abortMergeWhenConflicts = state.abortMergeWhenConflicts
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}
