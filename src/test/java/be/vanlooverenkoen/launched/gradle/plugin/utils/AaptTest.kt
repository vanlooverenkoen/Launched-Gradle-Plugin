package be.vanlooverenkoen.launched.gradle.plugin.utils

import org.junit.Assert
import org.junit.Test

/**
 * @author Koen Van Looveren
 */
class AaptTest {

    @Test
    fun testRegexPackageName1() {
        val packageName = "be.vanlooverenkoen.app"
        val result = "name='$packageName'"


        val output = AaptHelper.matchPackageName(result)
        Assert.assertEquals("Should be $packageName", packageName, output)
    }

    @Test
    fun testRegexPackageName2() {
        val packageName = "be.vanlooverenkoen.app"
        val result = " name='$packageName'"

        val output = AaptHelper.matchPackageName(result)
        Assert.assertEquals("Should be $packageName", packageName, output)
    }

    @Test
    fun testRegexPackageName3() {
        val packageName = "be.vanlooverenkoen.app"
        val result = "name='$packageName' "

        val output = AaptHelper.matchPackageName(result)
        Assert.assertEquals("Should be $packageName", packageName, output)
    }

    @Test
    fun testRegexPackageName4() {
        val packageName = "be.vanlooverenkoen.app"
        val result = " name='$packageName' "

        val output = AaptHelper.matchPackageName(result)
        Assert.assertEquals("Should be $packageName", packageName, output)
    }

    @Test
    fun testRegexVersionName1() {
        val versionName = "1.0.0"
        val result = "versionName='$versionName'"

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName2() {
        val versionName = "1.0.0"
        val result = " versionName='$versionName'"

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName3() {
        val versionName = "1.0.0"
        val result = "versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName4() {
        val versionName = "1.0.0"
        val result = " versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName5() {
        val versionName = "1.0.0-Dev"
        val result = " versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName6() {
        val versionName = "Dev.1.0.0"
        val result = " versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName7() {
        val versionName = "Dev.&$#!@(#*()-2176751.0.0"
        val result = " versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName8() {
        val versionName = "Dev.&$#!@(#*()-2176751.0.0Dev.&$#!@(#*()-"
        val result = " versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName9() {
        val versionName = ".&$#!@(#*()-2176751.0.0.&$#!@(#*()-"
        val result = " versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionName10() {
        val versionName = "&$#!@(#*()-2176751.0.0&$#!@(#*()-"
        val result = " versionName='$versionName' "

        val output = AaptHelper.matchVersionName(result)
        Assert.assertEquals("Should be $versionName", versionName, output)
    }

    @Test
    fun testRegexVersionCode1() {
        val versionCode = "1"
        val result = "versionCode='$versionCode'"

        val output = AaptHelper.matchVersionCode(result)
        Assert.assertEquals("Should be $versionCode", versionCode, output)
    }

    @Test
    fun testRegexVersionCode2() {
        val versionCode = "1"
        val result = " versionCode='$versionCode'"

        val output = AaptHelper.matchVersionCode(result)
        Assert.assertEquals("Should be $versionCode", versionCode, output)
    }

    @Test
    fun testRegexVersionCode3() {
        val versionCode = "1"
        val result = "versionCode='$versionCode' "

        val output = AaptHelper.matchVersionCode(result)
        Assert.assertEquals("Should be $versionCode", versionCode, output)
    }

    @Test
    fun testRegexVersionCode4() {
        val versionCode = "1"
        val result = " versionCode='$versionCode' "

        val output = AaptHelper.matchVersionCode(result)
        Assert.assertEquals("Should be $versionCode", versionCode, output)
    }

    @Test
    fun testRegexVersionCode5() {
        val versionCode = "1.0"
        val result = " versionCode='$versionCode' "

        val output = AaptHelper.matchVersionCode(result)
        Assert.assertEquals("Should be empty", "", output)
    }

    @Test
    fun testRegexVersionCode6() {
        val versionCode = "17382974983274"
        val result = " versionCode='$versionCode' "

        val output = AaptHelper.matchVersionCode(result)
        Assert.assertEquals("Should be empty", versionCode, output)
    }
}