### Launched Gradle Plugin ###

**Before You Start**

Supported Kotlin version 1.2.30 and above
Make sure git is installed and added to your PATH
Make sure the latest Android Build Tools are installed and added to your PATH (needed for aapt)

**How To Use**

Import in root gradle

    buildscript {
        repositories {
            ...
            jcenter()
            ...
        }
        dependencies {
            ...
            classpath "be.vanlooverenkoen.launched:plugin:{latest-version}"
            ...
        }
    }

Apply the plugin by adding next command at the top of your app/build.gradle file

    apply plugin: 'launched-gradle-plugin'

    LaunchedBuildConfig {
        apiKey "your-api-key"
        apiUrl "your-api-url"

        detekt {
        }
    }