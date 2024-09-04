package com.github.iml885203.intellijgitmergeinto.settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel

class ProjectSettingsComponent {
    private val myMainPanel: JPanel
    private val myTargetBranchOverwrite = JBCheckBox("Overwrite")
    private val myTargetBranchText = JBTextField()

    init {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.X_AXIS) // 水平佈局
        panel.add(myTargetBranchText)
        panel.add(myTargetBranchOverwrite)

        myTargetBranchOverwrite.addActionListener {
            myTargetBranchText.isEnabled = myTargetBranchOverwrite.isSelected
        }

        myTargetBranchText.isEnabled = myTargetBranchOverwrite.isSelected

        myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Target branch:", panel, 1, false)
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

    var targetBranchOverwrite: Boolean
        get() = this.myTargetBranchOverwrite.isSelected
        set(newStatus) {
            this.myTargetBranchOverwrite.isSelected = newStatus
            this.myTargetBranchText.isEnabled = newStatus
        }
}
