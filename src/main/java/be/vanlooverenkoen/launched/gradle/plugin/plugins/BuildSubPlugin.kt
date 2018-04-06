package be.vanlooverenkoen.launched.gradle.plugin.plugins

import be.vanlooverenkoen.launched.gradle.plugin.config.BuildExtension
import org.gradle.api.Project

/**
 * @author Koen Van Looveren
 */
interface BuildSubPlugin {

    fun init(project: Project) {}

    fun configure(project: Project, configuration: BuildExtension)

}