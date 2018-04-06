package be.vanlooverenkoen.launched.gradle.plugin.tasks.deploy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Koen Van Looveren
 */
open class LaunchItTask : DefaultTask() {

    lateinit var apiUrl: String
    lateinit var apiKey: String
    lateinit var apkFilePath: String
    lateinit var releaseNotes: String
    lateinit var variantName: String
    var sendMail: Boolean = false
    var public: Boolean = false

    @TaskAction
    fun launch() {
        val launchIt = LaunchIt(apiUrl, apiKey)
        launchIt.uploadApp(apkFilePath, releaseNotes, variantName, public, sendMail)
    }
}