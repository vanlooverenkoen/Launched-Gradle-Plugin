package be.vanlooverenkoen.launched.gradle.plugin.tasks.status

import be.vanlooverenkoen.launched.gradle.plugin.utils.GitHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Koen Van Looveren
 */
open class CheckCleanRepoTask : DefaultTask() {

    @TaskAction
    fun checkCleanRepo() {
        GitHelper.ensureCleanRepo()
    }

}