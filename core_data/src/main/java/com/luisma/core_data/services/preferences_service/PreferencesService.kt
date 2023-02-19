package com.luisma.core_data.services.preferences_service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.luisma.core.services.preferences_service.IPreferencesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesService(
    private val dataStore: DataStore<Preferences>,
) : IPreferencesService {


    override suspend fun setString(key: String, value: String) {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }

    override fun getString(key: String): Flow<String> {
        return dataStore.data.map { value ->
            value[stringPreferencesKey(key)] ?: ""
        }
    }

    override suspend fun setBool(key: String, value: Boolean) {
        dataStore.edit { settings ->
            settings[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun getBool(key: String): Flow<Boolean> {
        return dataStore.data.map { value ->
            value[booleanPreferencesKey(key)] ?: false
        }
    }
}