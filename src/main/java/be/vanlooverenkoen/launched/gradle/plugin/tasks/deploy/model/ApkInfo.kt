package be.vanlooverenkoen.launched.gradle.plugin.tasks.deploy.model

/**
 * @author Koen Van Looveren
 */
class ApkInfo(val name: String, val packageName: String, val versionName: String, val versionCode: String) {
    init {
        if (name.isEmpty())
            throw IllegalArgumentException("Invalid name. Name is empty")
        if (packageName.isEmpty())
            throw IllegalArgumentException("Invalid packageName. PackageName is empty")
        if (versionName.isEmpty())
            throw IllegalArgumentException("Invalid versionName. VersionName is empty")
        if (versionCode.isEmpty())
            throw IllegalArgumentException("Invalid versionCode. VersionCode is empty")
    }
}