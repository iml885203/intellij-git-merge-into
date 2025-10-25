package com.github.iml885203.intellijgitmergeinto

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import git4idea.commands.Git
import git4idea.commands.GitCommand
import git4idea.commands.GitLineHandler
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

class GitCommander(private var project: Project) {
    private val repository: GitRepository?
    private fun repoRoot() = repository!!.getRoot()

    init {
        repository = findMainRepository(project)
    }

    fun repositoryNotFound() = repository == null
    fun branchNotFound() = repository?.currentBranch?.name == null
    fun getCurrentBranch() = repository?.currentBranch?.name ?: ""

    private fun findMainRepository(project: Project): GitRepository? {
        val repositories = GitRepositoryManager.getInstance(project).repositories
        return repositories.firstOrNull { it.getRoot().path == project.basePath }
    }

    fun execute(command: GitCommand, params: Array<String>) {
        val handler = GitLineHandler(project, repoRoot(), command)
        handler.addParameters(*params)
        val result = Git.getInstance().runCommand(handler)
        if (!result.success()) {
            throw RuntimeException(result.errorOutputAsJoinedString)
        }
    }

    fun checkUncommittedChanges() {
        val handler = GitLineHandler(project, repoRoot(), GitCommand.STATUS)
        handler.addParameters("--porcelain")
        val result  = Git.getInstance().runCommand(handler)
        if (!result.success()) {
            throw RuntimeException(result.errorOutputAsJoinedString)
        }
        if (result.getOutput().isNotEmpty()) {
            throw MergeIntoException(EnumErrorCode.UncommittedChanges, "There are uncommitted changes.")
        }
    }

    fun refreshVcsChanges() {
        val dirtyScopeManager = VcsDirtyScopeManager.getInstance(project)
        dirtyScopeManager.markEverythingDirty()
    }
}
