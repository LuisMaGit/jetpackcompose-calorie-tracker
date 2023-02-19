apply {
    from("$rootDir/base-compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.coreTest))
    "implementation"(project(Modules.trackerDomain))
    "testImplementation"(Test.junit4)
    "testImplementation"(Test.mockkAndroid)
    "testImplementation"(Test.mockkAgent)
    "testImplementation"(Test.testCoroutines)
    "testImplementation"(Test.turbine)
}
