package be.vanlooverenkoen.launched.gradle.plugin.utils

import be.vanlooverenkoen.launched.gradle.plugin.tasks.deploy.model.ApkInfo

/**
 * @author Koen Van Looveren
 */
object AaptHelper {

    private const val APP_NAME_REGEX = ".*application-label:'([^']*)'.*"
    private const val VERSION_NAME_REGEX = ".*versionName='([a-zA-Z\\-0-9.!@#\$%^&*()]*)'.*"
    private const val VERSION_CODE_REGEX = ".*versionCode='([0-9]*)'.*"
    private const val PACKAGE_NAME_REGEX = ".*name='([a-zA-Z\\-0-9.!@#\$%^&*()]*]*)'.*"

    fun getApkInfo(path: String): ApkInfo {
        val result = ShellHelper.exec(listOf("aapt", "dump", "badging", path))
        val apkInfo = ApkInfo(
                matchAppName(result),
                matchPackageName(result),
                matchVersionName(result),
                matchVersionCode(result)
        )
        println(result)
        return apkInfo
    }

    fun matchAppName(stringToSearch: String): String {
        return APP_NAME_REGEX.toRegex().matchEntire(stringToSearch)?.groups?.get(1)?.value ?: ""
    }

    fun matchPackageName(stringToSearch: String): String {
        return PACKAGE_NAME_REGEX.toRegex().matchEntire(stringToSearch)?.groups?.get(1)?.value ?: ""
    }

    fun matchVersionCode(stringToSearch: String): String {
        return VERSION_CODE_REGEX.toRegex().matchEntire(stringToSearch)?.groups?.get(1)?.value ?: ""
    }

    fun matchVersionName(stringToSearch: String): String {
        return VERSION_NAME_REGEX.toRegex().matchEntire(stringToSearch)?.groups?.get(1)?.value ?: ""
    }
}