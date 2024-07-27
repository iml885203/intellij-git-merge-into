package com.github.iml885203.intellijgitmergeinto

import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.vcs.console.ShowVcsConsoleTabAction

object MyNotifier {
    fun notifyFailed(project: Project, content: String) {
        val notificationGroup = NotificationGroupManager.getInstance()
            .getNotificationGroup("Git Merge Into")
        val notification = notificationGroup
            .createNotification("Git merge into failed", content, NotificationType.WARNING)
        notification.addAction(object : NotificationAction("Open git console") {
            override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                ShowVcsConsoleTabAction().actionPerformed(e)
            }
        })
        notification.notify(project)
    }
}
