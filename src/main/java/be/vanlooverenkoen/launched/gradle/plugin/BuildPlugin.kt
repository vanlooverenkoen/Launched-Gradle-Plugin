package be.vanlooverenkoen.launched.gradle.plugin

import be.vanlooverenkoen.launched.gradle.plugin.config.BuildExtension
import be.vanlooverenkoen.launched.gradle.plugin.plugins.BuildSubPlugin
import be.vanlooverenkoen.launched.gradle.plugin.plugins.codequality.DetektPlugin
import be.vanlooverenkoen.launched.gradle.plugin.plugins.deploy.LaunchItPlugin
import be.vanlooverenkoen.launched.gradle.plugin.plugins.status.GitStatusPlugin
import be.vanlooverenkoen.launched.gradle.plugin.plugins.versionbump.VersionBumpPlugin
import be.vanlooverenkoen.launched.gradle.plugin.utils.GitHelper
import be.vanlooverenkoen.launched.gradle.plugin.utils.VersionBumpHelper
import be.vanlooverenkoen.launched.gradle.plugin.utils.removeFirst
import be.vanlooverenkoen.launched.gradle.plugin.utils.replaceAll
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.http.util.TextUtils

/**
 * @author Koen Van Looveren
 */
open class BuildPlugin : Plugin<Project> {

    private val subPlugins = mutableListOf<BuildSubPlugin>()

    override fun apply(project: Project) {
        if (!project.plugins.hasPlugin(AppPlugin::class.java)) {
            project.logger.debug("This plugin is made for Android Application Projects. The Android Plugin needs to be applied before this plugin.")
            return
        }

        subPlugins.replaceAll(GitStatusPlugin(),
                VersionBumpPlugin(),
                DetektPlugin(),
                LaunchItPlugin())

        val extension = project.extensions.create(CONFIG_NAME, BuildExtension::class.java, project)
        project.gradle.startParameter.taskNames.forEach {
            if (it.startsWith("deploy")) {
                val name = it.removeFirst("deploy")
                if (TextUtils.isEmpty(name)) return@forEach
                val list = VersionBumpHelper.versionBump(name)
                list.forEach {
                    project.setProperty(it.first, it.second.toString())
                    project.rootProject.setProperty(it.first, it.second.toString())
                }
                GitHelper.pushToOrigin()
            }
        }

        project.afterEvaluate {
            val variants = project.extensions.findByType(AppExtension::class.java).applicationVariants
            VersionBumpHelper.init(variants.map { it.name })

            subPlugins.forEach { it.configure(project, extension) }
        }
    }

    companion object {
        private const val CONFIG_NAME = "LaunchedBuildConfig"
    }
}
