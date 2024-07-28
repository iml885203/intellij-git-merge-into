package com.github.iml885203.intellijgitmergeinto

import com.intellij.openapi.project.Project
import git4idea.commands.Git
import git4idea.commands.GitCommand
import git4idea.commands.GitLineHandler
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

class GitHelper {
    companion object {
        fun findMainRepository(project: Project): GitRepository? {
            val repositories = GitRepositoryManager.getInstance(project).repositories
            return repositories.firstOrNull { it.root.path == project.basePath }
        }

        fun execute(project: Project, repository: GitRepository, command: GitCommand, params: Array<String>) {
            val handler = GitLineHandler(project, repository.root, command)
            for (param in params) {
                handler.addParameters(param)
            }
            Git.getInstance().runCommand(handler).throwOnError()
        }

        fun checkUncommittedChanges(project: Project, repository: GitRepository) {
            val handler = GitLineHandler(project, repository.root, GitCommand.STATUS)
            handler.addParameters("--porcelain")
            val result  = Git.getInstance().runCommand(handler)
            result.throwOnError()
            if (result.output.isNotEmpty()) {
                throw IllegalStateException("There are uncommitted changes in the current branch.")
            }
        }
    }
}
