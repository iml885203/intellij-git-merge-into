package com.github.iml885203.intellijgitmergeinto

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object MyNotifier {
    fun notifyFailed(project: Project, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Git Merge")
            .createNotification("Git Merge Failed", content, NotificationType.WARNING)
            .notify(project)
    }
}
