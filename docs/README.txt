### Launched Gradle Plugin ###

**How To Use**

Supported Kotlin version 1.2.30 and above

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
