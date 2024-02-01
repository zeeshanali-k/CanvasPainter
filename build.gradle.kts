//buildscript {
//    repositories {
//        mavenCentral()
//    }
//}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.kotlin.android").version("1.9.21").apply(false)
    id("com.vanniktech.maven.publish").version("0.25.3").apply(false)

    kotlin("multiplatform").apply(false)
    id("org.jetbrains.compose").apply(false)
}
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
