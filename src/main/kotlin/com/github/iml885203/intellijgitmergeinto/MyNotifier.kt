package com.github.iml885203.intellijgitmergeinto

import com.intellij.icons.AllIcons
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.vcs.console.ShowVcsConsoleTabAction
import git4idea.actions.GitResolveConflictsAction

class MyNotifier(private var project: Project) {
    fun notifyFailed(content: String) {
        createNotification("Git merge into failed", content, NotificationType.WARNING)
            .notify(project)
    }

    fun notifySuccess(content: String) {
        createNotification("Git merge into success", content, NotificationType.INFORMATION)
            .notify(project)
    }

    fun notifyConflict(content: String, abortAndBackCallback: Runnable) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Git Merge Into")
            .createNotification("Git merge into conflict", content, NotificationType.ERROR)
            .addAction(object : NotificationAction("Resolve conflicts") {
                init {
                    templatePresentation.icon = AllIcons.Diff.ApplyNotConflicts
                }
                override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                    val action = ActionManager.getInstance().getAction("Git.ResolveConflicts")
                    action?.actionPerformed(e)
                }
            })
            .addAction(object : NotificationAction("Abort and back"){
                init {
                    templatePresentation.icon = AllIcons.Actions.Rollback
                }
                override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                    abortAndBackCallback.run()
                    notification.expire()
                }
            })
            .notify(project)
    }

    private fun createNotification(title: String, content: String, type: NotificationType): Notification {
        return NotificationGroupManager.getInstance()
            .getNotificationGroup("Git Merge Into")
            .createNotification(title, content, type)
            .addAction(object : NotificationAction("Show detail") {
                override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                    val action = ActionManager.getInstance().getAction("Vcs.ShowConsoleTab")
                    action?.actionPerformed(e)
                }
            })
    }
}
