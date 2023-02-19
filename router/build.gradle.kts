apply {
    from("$rootDir/base-compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.startupUI))
    "implementation"(project(Modules.onboardingUI))
    "implementation"(project(Modules.trackerUI))
}