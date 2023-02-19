object Build {
    private const val androidBuildToolsVersion = "7.1.1"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    const val hiltAndroidGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${DaggerHilt.version}"

    const val kotlinSerializationPlugin =
        "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"

    const val sqlDelightGradlePlugin =
        "com.squareup.sqldelight:gradle-plugin:${SqlDelight.sqlDelightVersion}"
}