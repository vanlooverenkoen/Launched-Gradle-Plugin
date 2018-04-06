package be.vanlooverenkoen.launched.gradle.plugin.tasks.status

import be.vanlooverenkoen.launched.gradle.plugin.utils.GitHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Koen Van Looveren
 */
open class CommitTask : DefaultTask() {

    lateinit var message: String

    @TaskAction
    fun commitAndPush() {
        GitHelper.addAndCommit(message)
        GitHelper.pushToOrigin()
    }

}