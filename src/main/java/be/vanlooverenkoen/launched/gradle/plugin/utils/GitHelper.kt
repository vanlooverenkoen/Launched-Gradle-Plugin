package be.vanlooverenkoen.launched.gradle.plugin.utils

import joptsimple.internal.Strings
import java.util.*
import java.util.regex.Pattern

/**
 * @author Koen Van Looveren
 */
object GitHelper {

    fun isNoGitRepo(): Boolean {
        val result = ShellHelper.exec(listOf("git", "status"))
        return result == "fatal: Not a git repository (or any of the parent directories): .git"
    }

    fun add() {
        if (isNoGitRepo()) return
        ShellHelper.exec(listOf("git", "add", "."))
    }

    fun commit(message: String) {
        if (isNoGitRepo()) return
        ShellHelper.exec(listOf("git", "commit", "-m", message))
    }

    fun addAndCommit(message: String) {
        if (isNoGitRepo()) return
        add()
        commit(message)
    }

    fun ensureCleanRepo() {
        if (isNoGitRepo()) return
        val output = ShellHelper.exec(listOf("git", "status", "--porcelain"))
        val regex = "\\s*M gradle.properties\\s*"
        val cleanOutput = output.removeRegex(regex)
        if (!Strings.isNullOrEmpty(cleanOutput)) {
            println("Git is not clean!")
            println("> Output (ignore the starting and end quotes):")
            println(">> '$output'")
            println("> Clean Output was (ignore the starting and end quotes):")
            println(">> '$cleanOutput'")
            throw RuntimeException("Make sure your git repo is clean")
        }
    }

    fun pushToOrigin() {
        if (isNoGitRepo()) return
        val remoteBranch: String? = System.getenv("GIT_BRANCH")
        val correctRemoteBranch: String? = remoteBranch?.substring(0 until remoteBranch.indexOf('/'))
        val localBranch: String? = System.getenv("GIT_LOCAL_BRANCH")
        if ((correctRemoteBranch != null) && (localBranch != null)) {
            ShellHelper.exec(listOf("git", "push", correctRemoteBranch, localBranch))
        } else if ((correctRemoteBranch != null) && (localBranch == null)) {
            ShellHelper.exec(listOf("git", "push", correctRemoteBranch, getCurrentBranchName()))
        } else if (remoteBranch == null && localBranch != null) {
            ShellHelper.exec(listOf("git", "push", "origin", localBranch))
        } else {
            ShellHelper.exec(listOf("git", "push", "origin", getCurrentBranchName()))
        }
    }

    private fun getCurrentBranchName(): String {
        val output = ShellHelper.exec(listOf("git", "branch"), newLine = true)
        val regex = "\\* (.*)"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(output)
        if (matcher.find()) {
            return matcher.group(1)
        } else {
            throw RuntimeException("Could not parse your origin url.")
        }
    }
}