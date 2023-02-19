package com.luisma.core.services.preferences_service

import kotlinx.coroutines.flow.Flow

class FakePreferencesService : IPreferencesService {

    override suspend fun setString(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun getString(key: String): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun setBool(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getBool(key: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}