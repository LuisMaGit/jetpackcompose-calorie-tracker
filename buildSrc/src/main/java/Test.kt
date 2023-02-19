object Test {
    private const val junitVersion = "4.13.2"
    const val junit4 = "junit:junit:$junitVersion"

    private const val testCoroutinesVersion = "1.6.4"
     const val testCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${testCoroutinesVersion}"

    private const val mockkVersion = "1.13.3"
    const val mockkAndroid = "io.mockk:mockk-android:${mockkVersion}"
    const val mockkAgent = "io.mockk:mockk-agent:${mockkVersion}"

    private const val turbineVersion = "0.12.1"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"

//    private const val junitAndroidExtVersion = "1.1.3"
//    const val junitAndroidExt = "androidx.test.ext:junit:$junitAndroidExtVersion"
//    const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"
}