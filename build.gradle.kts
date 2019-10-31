plugins {
    kotlin("jvm") version "1.3.50"
}
buildscript {
    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath(Dependencies.Classpath.kotlinGradle)
        classpath(Dependencies.Classpath.androidTools)
        classpath(Dependencies.Classpath.dokkaPlugin)
    }
}

subprojects {
    group = "com.noheltcj"
    version = Versions.starter

    repositories {
        jcenter()
        google()
    }
}
