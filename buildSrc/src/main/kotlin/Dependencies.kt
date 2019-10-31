object Dependencies {
    const val rxcommon = "com.noheltcj:rxcommon:${Versions.rxcommon}"

    object JVM {
        const val inject = "javax.inject:javax.inject:${Versions.JVM.inject}"
        const val gson = "com.google.code.gson:gson:${Versions.JVM.gson}"
    }

    object Classpath {
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val androidTools = "com.android.tools.build:gradle:${Versions.Android.gradleTools}"
        const val dokkaPlugin = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    }
}