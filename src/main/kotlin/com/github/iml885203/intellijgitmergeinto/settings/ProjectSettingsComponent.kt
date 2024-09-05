package com.github.iml885203.intellijgitmergeinto.settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel

class ProjectSettingsComponent {
    private val myMainPanel: JPanel
    private val myTargetBranchOverwrite = JBCheckBox("Overwrite")
    private val myTargetBranchText = JBTextField()
    private val myPushAfterMergeOverwrite = JBCheckBox("Overwrite")
    private val myPushAfterMerge = JBCheckBox("Push after merge")

    init {
        val targetBranchPanel = JPanel()
        targetBranchPanel.layout = BoxLayout(targetBranchPanel, BoxLayout.X_AXIS) // 水平佈局
        targetBranchPanel.add(myTargetBranchText)
        targetBranchPanel.add(myTargetBranchOverwrite)
        myTargetBranchOverwrite.addActionListener {
            myTargetBranchText.isEnabled = myTargetBranchOverwrite.isSelected
        }
        myTargetBranchText.isEnabled = myTargetBranchOverwrite.isSelected

        val pushAfterMergePanel = JPanel()
        pushAfterMergePanel.layout = BoxLayout(pushAfterMergePanel, BoxLayout.X_AXIS) // 水平佈局
        pushAfterMergePanel.add(myPushAfterMerge)
        pushAfterMergePanel.add(Box.createHorizontalGlue()) // 添加彈性空間
        pushAfterMergePanel.add(myPushAfterMergeOverwrite)
        myPushAfterMergeOverwrite.addActionListener {
            myPushAfterMerge.isEnabled = myPushAfterMergeOverwrite.isSelected
        }
        myPushAfterMerge.isEnabled = myPushAfterMergeOverwrite.isSelected

        myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Target branch:", targetBranchPanel, 1, false)
            .addComponent(pushAfterMergePanel, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val panel: JPanel
        get() = myMainPanel

    val preferredFocusedComponent: JComponent
        get() = this.myTargetBranchText

    var targetBranchOverwrite: Boolean
        get() = this.myTargetBranchOverwrite.isSelected
        set(newStatus) {
            this.myTargetBranchOverwrite.isSelected = newStatus
            this.myTargetBranchText.isEnabled = newStatus
        }

    var targetBranchText: String
        get() = this.myTargetBranchText.text
        set(newText) {
            this.myTargetBranchText.text = newText
        }

    var pushAfterMergeOverwrite: Boolean
        get() = this.myPushAfterMergeOverwrite.isSelected
        set(newStatus) {
            this.myPushAfterMergeOverwrite.isSelected = newStatus
            this.myPushAfterMerge.isEnabled = newStatus
        }

    var pushAfterMerge: Boolean
        get() = this.myPushAfterMerge.isSelected
        set(newStatus) {
            this.myPushAfterMerge.isSelected = newStatus
        }
}
