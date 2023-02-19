package com.luisma.core.services.preferences_service

import kotlinx.coroutines.flow.Flow

interface IPreferencesService {
    suspend fun setString(key: String, value: String)
    fun getString(key: String): Flow<String>
    suspend fun setBool(key: String, value: Boolean)
    suspend fun getBool(key: String): Flow<Boolean>
}

object PreferencesContracts {
    enum class Keys(name: String) {
        UserProfile("user_profile"),
        UserOnboarded("user_onboarded")
    }

    const val STORE_NAME = "CALORIE_TRACKER_PREFERENCES"
}
