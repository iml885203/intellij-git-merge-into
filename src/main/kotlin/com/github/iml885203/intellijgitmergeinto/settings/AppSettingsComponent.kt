package com.github.iml885203.intellijgitmergeinto.settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AppSettingsComponent {
    private val myMainPanel: JPanel
    private val myTargetBranchText = JBTextField()
    private val myPushAfterMerge = JBCheckBox("Push after merge")
    private val myRunInBackground = JBCheckBox("Run in background")
    private val myAbortMergeWhenConflicts = JBCheckBox("Abort merge when conflicts")

    init {
        myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Target branch:"), this.myTargetBranchText, 1, false)
            .addComponent(myPushAfterMerge, 1)
            .addComponent(myRunInBackground, 1)
            .addComponent(myAbortMergeWhenConflicts, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val panel: JPanel
        get() = myMainPanel

    val preferredFocusedComponent: JComponent
        get() = this.myTargetBranchText

    var targetBranchText: String
        get() = this.myTargetBranchText.text
        set(newText) {
            this.myTargetBranchText.text = newText
        }

    var pushAfterMerge: Boolean
        get() = myPushAfterMerge.isSelected
        set(newStatus) {
            myPushAfterMerge.isSelected = newStatus
        }

    var runInBackground: Boolean
        get() = myRunInBackground.isSelected
        set(newStatus) {
            myRunInBackground.isSelected = newStatus
        }

    var abortMergeWhenConflicts: Boolean
        get() = myAbortMergeWhenConflicts.isSelected
        set(newStatus) {
            myAbortMergeWhenConflicts.isSelected = newStatus
        }
}
