package com.github.iml885203.intellijgitmergeinto.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.application.ApplicationManager

@State(
    name = "com.github.iml885203.intellijgitmergeinto.settings.AppSettings",
    storages = [Storage("GitMergeIntoSettings.xml")]
)
class AppSettings : PersistentStateComponent<AppSettings.State> {

    class State {
        var targetBranch: String = "develop"
        var pushAfterMerge: Boolean = true
        var runInBackground: Boolean = false
    }

    private var myState: State = State()

    companion object {
        val instance: AppSettings
            get() = ApplicationManager.getApplication().getService(AppSettings::class.java)
    }

    override fun getState(): State {
        return myState
    }

    override fun loadState(state: State) {
        myState = state
    }
}
