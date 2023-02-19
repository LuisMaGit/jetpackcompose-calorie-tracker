apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Test.junit4)
    "implementation"(Test.mockkAndroid)
    "implementation"(Test.mockkAgent)
    "implementation"(Test.testCoroutines)
    "implementation"(Test.turbine)
}