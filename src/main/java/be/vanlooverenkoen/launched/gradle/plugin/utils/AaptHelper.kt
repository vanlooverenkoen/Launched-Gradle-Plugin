package be.vanlooverenkoen.launched.gradle.plugin.utils

import be.vanlooverenkoen.launched.gradle.plugin.tasks.deploy.model.ApkInfo

/**
 * @author Koen Van Looveren
 */
object AaptHelper {

    fun getApkInfo(path: String): ApkInfo {
        val result = ShellHelper.exec(listOf("aapt", "dump", "badging", path))
        val apkInfo = ApkInfo(
                ".*application-label:'([^']*)'.*".toRegex().matchEntire(result)?.groups?.get(1)?.value ?: "",
                ".*package: name='([a-z0-9.]*)'.*".toRegex().matchEntire(result)?.groups?.get(1)?.value ?: "",
                ".* versionName='([a-z0-9.]*)'.*".toRegex().matchEntire(result)?.groups?.get(1)?.value ?: "",
                ".* versionCode='([0-9]*)'.*".toRegex().matchEntire(result)?.groups?.get(1)?.value ?: ""
        )
        println(result)
        return apkInfo
    }
}