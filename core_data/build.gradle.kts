apply {
    from("$rootDir/base-module.gradle")
}

plugins {
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
}

dependencies {
    "implementation"(DataStore.dataStore)
    "implementation"(project(Modules.core))

    "implementation"(Serialization.kotlinSerializationJsonDependency)

    "implementation"(Ktor.ktorCore)
    "implementation"(Ktor.ktorAndroidClient)

    "implementation"(SqlDelight.sqlDelightAndroidDriver)

    "implementation"(Test.junit4)
    "implementation"(Test.mockkAndroid)
    "implementation"(Test.mockkAgent)
    "implementation"(Test.testCoroutines)
    "implementation"(Test.turbine)
}