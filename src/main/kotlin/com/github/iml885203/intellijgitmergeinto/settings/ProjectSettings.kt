package com.github.iml885203.intellijgitmergeinto.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project

@State(
    name = "com.github.iml885203.intellijgitmergeinto.settings.ProjectSettings",
    storages = [Storage("GitMergeIntoProjectSettings.xml")]
)
class ProjectSettings : PersistentStateComponent<ProjectSettings.State> {

    class State {
        var targetBranchOverwrite: Boolean = false
        var targetBranch: String = "develop"
    }

    private var myState: State = State()

    companion object {
        fun getInstance(project: Project): ProjectSettings {
            return project.getService(ProjectSettings::class.java)
        }
    }

    override fun getState(): State {
        return myState
    }

    override fun loadState(state: State) {
        myState = state
    }
}
