package be.vanlooverenkoen.launched.gradle.plugin.config

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Action
import org.gradle.api.Project

/**
 * @author Koen Van Looveren
 */
open class BuildExtension(private val project: Project) {

    var apiKey: String? = null
    var apiUrl: String? = null
    var releaseNotes: String = "No release notes were given"
    var publicApp: Boolean = false
    var sendMail: Boolean = true

    var detektConfig: DetektExtension? = null

    open fun detekt(configuration: Action<in DetektExtension>) {
        detektConfig = DetektExtension().apply { configuration.execute(this) }
    }

}
