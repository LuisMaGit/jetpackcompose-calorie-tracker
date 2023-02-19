package com.luisma.core_data.services.preferences_service

import com.luisma.core.models.UserProfile
import com.luisma.core.services.JsonSerializationService
import com.luisma.core.services.preferences_service.IPreferencesService
import com.luisma.core.services.preferences_service.IUserPreferencesService
import com.luisma.core.services.preferences_service.PreferencesContracts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfilePreferenceService(
    private val jsonSerializationService: JsonSerializationService,
    private val preferencesService: IPreferencesService
) : IUserPreferencesService {
    override suspend fun setUserProfile(userProfile: UserProfile) {
        val prefJson = UserProfilePref.fromCore(userProfile).toJson(
            jsonSerializationService
        )
        preferencesService.setString(
            key = PreferencesContracts.Keys.UserProfile.name,
            value = prefJson
        )
    }

    override suspend fun getUserProfile(): Flow<UserProfile> {
        return preferencesService.getString(
            PreferencesContracts.Keys.UserProfile.name
        ).map { pref ->
            if (pref.isEmpty()) {
                UserProfile()
            } else {
                UserProfilePref.fromJson(
                    data = pref,
                    json = jsonSerializationService
                ).toCore()
            }
        }
    }

    override suspend fun setUserOnboarded() {
        preferencesService.setBool(
            key = PreferencesContracts.Keys.UserOnboarded.name,
            value = true
        )
    }

    override suspend fun getUserOnboarded(): Flow<Boolean> {
        return preferencesService.getBool(
            key = PreferencesContracts.Keys.UserOnboarded.name,
        )
    }
}