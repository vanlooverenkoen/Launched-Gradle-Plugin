package be.vanlooverenkoen.launched.gradle.plugin.plugins.deploy

import be.vanlooverenkoen.launched.gradle.plugin.config.BuildExtension
import be.vanlooverenkoen.launched.gradle.plugin.plugins.BuildSubPlugin
import be.vanlooverenkoen.launched.gradle.plugin.tasks.deploy.LaunchItTask
import com.android.build.gradle.AppExtension
import com.beust.jcommander.Strings
import org.gradle.api.Project

/**
 * @author Koen Van Looveren
 */
class LaunchItPlugin : BuildSubPlugin {

    override fun configure(project: Project, configuration: BuildExtension) {
        if (configuration.apiKey == null && configuration.apiUrl == null)
            return
        if (Strings.isStringEmpty(configuration.apiKey)) {
            throw IllegalAccessException("Invalid API-KEY")
        }
        if (Strings.isStringEmpty(configuration.apiUrl)) {
            throw IllegalAccessException("Invalid api url")
        }
        val tasks = mutableMapOf<String, MutableList<LaunchItTask>>()

        val androidExtension = project.extensions.getByType(AppExtension::class.java)

        androidExtension.applicationVariants.forEach { variant ->
            val versionBumpTask = project.tasks.create("deploy${variant.name.capitalize()}", LaunchItTask::class.java) {
                it.apiUrl = configuration.apiUrl.toString()
                it.apiKey = configuration.apiKey.toString()
                it.releaseNotes = configuration.releaseNotes
                it.variantName = variant.name
                it.sendMail = configuration.sendMail
                it.public = configuration.public
                it.apkFilePath = variant.outputs.first().outputFile.path
                it.dependsOn(variant.assemble)
            }
            val key = variant.buildType.name

            versionBumpTask.group = GROUP_NAME
            versionBumpTask.description = "${key.capitalize()} will be deployed to Launched"

            tasks.getOrPut(key, { mutableListOf() }).add(versionBumpTask)
        }
    }

    companion object {
        const val GROUP_NAME = "Launched - Deploy"
    }

}