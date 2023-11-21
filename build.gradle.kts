// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        //maven (url = "https://jitpack.io")
    }

    dependencies {
        val nav_version = "2.7.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath ("com.android.tools.build:bundletool:1.14.1")
        classpath ("com.android.tools.build:gradle:8.1.4")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        //classpath ("com.google.gms:google-services:4.4.0")

    }
}

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
