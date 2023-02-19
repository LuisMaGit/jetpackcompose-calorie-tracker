apply {
    from("$rootDir/base-module.gradle")
}


plugins {
    kotlin("plugin.serialization")
}

dependencies {
    "implementation"(Serialization.kotlinSerializationJsonDependency)
    "testImplementation"(Test.junit4)
}