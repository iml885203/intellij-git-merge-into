package com.github.iml885203.intellijgitmergeinto.actions

import com.github.iml885203.intellijgitmergeinto.MyNotifier
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import git4idea.commands.Git
import git4idea.commands.GitCommand
import git4idea.commands.GitLineHandler
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

class MergeIntoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val repository = findMainRepository(project)
        if (repository == null) {
            MyNotifier.notifyFailed(project, "No Git repository found")
            return
        }

        val currentBranch = repository.currentBranch?.name
        if (currentBranch == null) {
            MyNotifier.notifyFailed(project, "No current branch found")
            return
        }

        // TODO: Allow user to setting target branch in the settings
        val targetBranch = "develop"
        ProgressManager.getInstance().runProcessWithProgressSynchronously({
            mergeBranch(project, repository, currentBranch, targetBranch)
        }, "Merging Branch", true, project)
    }

    private fun findMainRepository(project: Project): GitRepository? {
        val repositories = GitRepositoryManager.getInstance(project).repositories
        return repositories.firstOrNull { it.root.path == project.basePath }
    }

    private fun mergeBranch(project: Project, repository: GitRepository, currentBranch: String, targetBranch: String) {
        val indicator = ProgressManager.getInstance().progressIndicator

        try {
            checkUncommittedChanges(project, repository)

            // Checkout develop branch
            updateProgress(indicator, "Checking out develop branch...", 0.01)
            executeGitCommand(project, repository, GitCommand.CHECKOUT, arrayOf(targetBranch))

            // Fetch latest changes
            updateProgress(indicator, "Fetching latest changes...", 0.1)
            executeGitCommand(project, repository, GitCommand.FETCH, arrayOf("origin", targetBranch))

            // Reset hard
            updateProgress(indicator, "Resetting hard...", 0.2)
            executeGitCommand(project, repository, GitCommand.RESET, arrayOf("--hard", "origin/$targetBranch"))

            // Merge current branch into develop
            updateProgress(indicator, "Merging current branch into ${targetBranch}...", 0.33)
            executeGitCommand(project, repository, GitCommand.MERGE, arrayOf(currentBranch))

            // Checkout back to the original branch
            updateProgress(indicator, "Checking out original branch...", 0.66)
            executeGitCommand(project, repository, GitCommand.CHECKOUT, arrayOf(currentBranch))

            indicator.fraction = 1.0
        } catch (ex: Exception) {
            handleMergeFailure(ex, currentBranch, project, repository)
        }
    }

    private fun handleMergeFailure(ex: Exception, currentBranch: String, project: Project, repository: GitRepository) {
        if (ex.message != null) {
            MyNotifier.notifyFailed(project, ex.message!!)
        }

        try {
            executeGitCommand(project, repository, GitCommand.CHECKOUT, arrayOf(currentBranch))
        } catch (checkoutEx: Exception) {
            thisLogger().error("Failed to checkout back to original branch: ${checkoutEx.message}")
        }

    }

    private fun checkUncommittedChanges(project: Project, repository: GitRepository) {
        val handler = GitLineHandler(project, repository.root, GitCommand.STATUS)
        handler.addParameters("--porcelain")
        val result  = Git.getInstance().runCommand(handler)
        result.throwOnError()
        if (result.output.isNotEmpty()) {
            throw IllegalStateException("There are uncommitted changes in the current branch.")
        }
    }

    private fun executeGitCommand(project: Project, repository: GitRepository, command: GitCommand, params: Array<String>) {
        val handler = GitLineHandler(project, repository.root, command)
        for (param in params) {
            handler.addParameters(param)
        }
        Git.getInstance().runCommand(handler).throwOnError()
    }

    private fun updateProgress(indicator: ProgressIndicator, text: String, fraction: Double) {
        indicator.text = text
        indicator.fraction = fraction
//        Thread.sleep(500) // Simulate progress
    }
}
