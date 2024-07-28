package com.github.iml885203.intellijgitmergeinto.actions

import com.github.iml885203.intellijgitmergeinto.*
import com.github.iml885203.intellijgitmergeinto.settings.AppSettings
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import git4idea.commands.GitCommand

class MergeIntoAction : AnAction() {
    private lateinit var gitCommander: GitCommander
    private lateinit var notifier: MyNotifier

    override fun actionPerformed(e: AnActionEvent ) {
        val project = e.project ?: return
        val state = AppSettings.instance.state
        gitCommander = GitCommander(project)
        notifier = MyNotifier(project)

        if (gitCommander.repositoryNotFound()) {
            notifier.notifyFailed("No Git repository found")
            return
        }

        if (gitCommander.branchNotFound()) {
            notifier.notifyFailed("No current branch found")
            return
        }

        ProgressManager.getInstance().runProcessWithProgressSynchronously({
            val targetBranch = state.targetBranch
            mergeBranch(gitCommander.getCurrentBranch(), targetBranch)
        }, "Merging Branch", true, project)

        // TODO: Allow user to setting run in background in the settings
//        ProgressManager.getInstance().run(object :
//            Task.Backgroundable(project,"Merging branch",true) {
//                override fun run(indicator: ProgressIndicator) {
//                    Thread.sleep(5_000)
//                }
//        })
    }

    private fun mergeBranch(currentBranch: String, targetBranch: String) {
        val indicator = ProgressManager.getInstance().progressIndicator
        val indicatorProcessor = IndicatorProcessor(indicator, 6)

        try {
            gitCommander.checkUncommittedChanges()

            // Checkout develop branch
            indicatorProcessor.nextStep("Checking out develop branch...")
            gitCommander.execute(GitCommand.CHECKOUT, arrayOf(targetBranch))

            // Fetch latest changes
            indicatorProcessor.nextStep("Fetching latest changes...")
            gitCommander.execute(GitCommand.FETCH, arrayOf("origin", targetBranch))

            // Reset hard
            indicatorProcessor.nextStep("Resetting hard...")
            gitCommander.execute(GitCommand.RESET, arrayOf("--hard", "origin/$targetBranch"))

            // Merge current branch into develop
            indicatorProcessor.nextStep("Merging current branch into ${targetBranch}...")
            gitCommander.execute(GitCommand.MERGE, arrayOf(currentBranch))

            // Push changes
            indicatorProcessor.nextStep("Pushing changes...")
            gitCommander.execute(GitCommand.PUSH, arrayOf("origin", targetBranch))

            // Checkout back to the original branch
            indicatorProcessor.nextStep("Checking out original branch...")
            gitCommander.execute(GitCommand.CHECKOUT, arrayOf(currentBranch))

            notifier.notifySuccess("Merge into $targetBranch completed.")
        } catch (ex: Exception) {
            thisLogger().warn(ex)
            handleMergeFailure(ex, indicator, currentBranch)
        }
    }

    private fun handleMergeFailure(ex: Exception, indicator: ProgressIndicator, currentBranch: String) {
        try {
            indicator.checkCanceled()
        } catch (cancelEx: Exception) {
            notifier.notifyFailed("Merge operation was cancelled.")
            return
        }

        val isUnknownError = ex.message == null
        if (isUnknownError) {
            notifier.notifyFailed("An error occurred during the merge operation. Exception: ${ex.javaClass.simpleName}")
            return
        }

        val isConflict = ex.message!!.contains("CONFLICT", ignoreCase = true)
        if (isConflict) {
            try {
                gitCommander.execute(GitCommand.MERGE, arrayOf("--abort"))
                gitCommander.execute(GitCommand.CHECKOUT, arrayOf(currentBranch))
                notifier.notifyFailed("Merge conflict occurred. Please resolve the conflict and try again.")
            } catch (abortEx: Exception) {
                thisLogger().error("Failed to abort merge: ${abortEx.message}")
            }
            return
        }

        try {
            gitCommander.execute(GitCommand.CHECKOUT, arrayOf(currentBranch))
            notifier.notifyFailed(ex.message!!)
        } catch (checkoutEx: Exception) {
            thisLogger().error("Failed to checkout back to original branch: ${checkoutEx.message}")
        }
    }
}
