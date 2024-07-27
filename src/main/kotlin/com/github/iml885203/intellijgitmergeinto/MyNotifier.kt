package com.github.iml885203.intellijgitmergeinto

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object MyNotifier {
    fun notifyFailed(project: Project, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Git Merge Into")
            .createNotification("Git merge into failed", content, NotificationType.WARNING)
            .notify(project)
    }
}
