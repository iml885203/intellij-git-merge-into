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
    private val myIdeaUserStatus = JBCheckBox("IntelliJ IDEA user")

    init {
        myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Target branch:"), this.myTargetBranchText, 1, false)
            .addComponent(myIdeaUserStatus, 1)
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

    var ideaUserStatus: Boolean
        get() = myIdeaUserStatus.isSelected
        set(newStatus) {
            myIdeaUserStatus.isSelected = newStatus
        }
}
