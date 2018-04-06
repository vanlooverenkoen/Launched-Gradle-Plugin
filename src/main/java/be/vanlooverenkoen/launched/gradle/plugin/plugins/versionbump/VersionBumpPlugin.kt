package be.vanlooverenkoen.launched.gradle.plugin.plugins.versionbump

import be.vanlooverenkoen.launched.gradle.plugin.config.BuildExtension
import be.vanlooverenkoen.launched.gradle.plugin.plugins.BuildSubPlugin
import be.vanlooverenkoen.launched.gradle.plugin.tasks.status.CheckCleanRepoTask
import be.vanlooverenkoen.launched.gradle.plugin.tasks.status.CommitTask
import be.vanlooverenkoen.launched.gradle.plugin.tasks.versionbump.VersionBumpTask
import com.android.build.gradle.AppExtension
import org.gradle.api.Project

/**
 * @author Koen Van Looveren
 */
class VersionBumpPlugin : BuildSubPlugin {

    override fun configure(project: Project, configuration: BuildExtension) {
        val tasks = mutableMapOf<String, MutableList<VersionBumpTask>>()
        val justTasks = mutableMapOf<String, MutableList<VersionBumpTask>>()

        val androidExtension = project.extensions.getByType(AppExtension::class.java)
        var count = 0

        androidExtension.applicationVariants.forEach { variant ->
            ++count
            val versionBumpTask = project.tasks.create("versionBump${variant.name.capitalize()}", VersionBumpTask::class.java) {
                it.flavorName = variant.name
            }
            val justVersionBumpTask = project.tasks.create("justVersionBump${variant.name.capitalize()}", VersionBumpTask::class.java) {
                it.flavorName = variant.name
                it.commit = false
            }
            val key = variant.buildType.name

            versionBumpTask.group = GROUP_NAME
            versionBumpTask.description = "A version bump will be executed of ${key.capitalize()}. Changes will be committed."

            justVersionBumpTask.group = GROUP_NAME
            justVersionBumpTask.description = "A version bump will be executed of ${key.capitalize()}. Changes will not be committed."

            tasks.getOrPut(key, { mutableListOf() }).add(versionBumpTask)
            justTasks.getOrPut(key, { mutableListOf() }).add(justVersionBumpTask)
        }

        if (count == androidExtension.buildTypes.size)
            return

        //There is need to add extra tasks that aggregate the version bumps over launchit types. Implement them as deps!

        androidExtension.buildTypes.forEach { buildType ->
            /* Version bump */
            val aggregate = project.tasks.create("versionBump${buildType.name.capitalize()}") { task ->

                task.doFirst {
                    CheckCleanRepoTask().apply {  }.checkCleanRepo()

                    tasks[buildType.name]?.forEach {
                        it.versionBump()
                    }
                    CommitTask().apply { message = "Version bump for ${buildType.name}" }.commitAndPush()
                }
            }
            aggregate.group = GROUP_NAME
            aggregate.description = "A version bump will be executed of ${buildType.name.capitalize()}. Changes will be committed."

            /* Just Version bump */
            val justAggregate = project.tasks.create("justVersionBump${buildType.name.capitalize()}") { task ->
                task.doFirst {
                    justTasks[buildType.name]?.forEach {
                        it.versionBump()
                    }
                }
            }
            justAggregate.group = GROUP_NAME
            justAggregate.description = "A version bump will be executed of ${buildType.name.capitalize()}. Changes will not be committed."
        }
    }

    companion object {
        const val GROUP_NAME = "Launched - Version bump"
    }

}