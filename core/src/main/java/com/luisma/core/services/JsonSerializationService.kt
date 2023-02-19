package com.luisma.core.services
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonSerializationService {
    val json = Json {
        ignoreUnknownKeys = true
    }

    inline fun <reified T> decodeFromString(string: String): T {
        return json.decodeFromString(string)
    }

    inline fun <reified T> encodeToString(value: T): String {
        return json.encodeToString(value)
    }
}