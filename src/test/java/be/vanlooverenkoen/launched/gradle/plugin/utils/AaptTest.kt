package be.vanlooverenkoen.launched.gradle.plugin.utils

import org.junit.Assert
import org.junit.Test

/**
 * @author Koen Van Looveren
 */
class AaptTest {

    @Test
    fun testRegexPackageName() {
        val packageName = "be.vanlooverenkoen.app"
        val versionCode = "1"
        val versionName = "1.0.0"
        val result = "name='$packageName' versionCode='$versionCode' versionName='$versionName'"

        val output = ".*name='([a-z0-9.]*)'.*".toRegex().matchEntire(result)?.groups?.get(1)?.value ?: ""
        Assert.assertEquals("Should be $packageName", packageName, output)
    }

    @Test
    fun testRegexVersionName() {
        val packageName = "be.vanlooverenkoen.app"
        val versionCode = "1"
        val versionName = "1.0.0"
        val result = "name='$packageName' versionCode='$versionCode' versionName='$versionName'"

        val output = ".*versionName='([a-z0-9.]*)'.*".toRegex().matchEntire(result)?.groups?.get(1)?.value ?: ""
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionCode() {
        val packageName = "be.vanlooverenkoen.app"
        val versionCode = "1"
        val versionName = "1.0.0"
        val result = "name='$packageName' versionCode='$versionCode' versionName='$versionName'"

        val output = ".*versionCode='([0-9]*)'.*".toRegex().matchEntire(result)?.groups?.get(1)?.value ?: ""
        Assert.assertEquals("Should be $versionCode", versionCode, output)
    }
}