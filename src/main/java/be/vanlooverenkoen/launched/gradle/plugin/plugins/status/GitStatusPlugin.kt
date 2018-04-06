package be.vanlooverenkoen.launched.gradle.plugin.plugins.status

import be.vanlooverenkoen.launched.gradle.plugin.config.BuildExtension
import be.vanlooverenkoen.launched.gradle.plugin.plugins.BuildSubPlugin
import be.vanlooverenkoen.launched.gradle.plugin.tasks.status.CheckCleanRepoTask
import org.gradle.api.Project

/**
 * @author Koen Van Looveren
 */
class GitStatusPlugin : BuildSubPlugin {

    override fun configure(project: Project, configuration: BuildExtension) {
        val task = project.tasks.create(CLEAN_GIT_TASK, CheckCleanRepoTask::class.java)
        task.description = "Will check if your git is clean. Otherwise an exception is thrown."
    }

    companion object {
        const val CLEAN_GIT_TASK = "ensureCleanRepo"
    }

}