package com.luisma.core.services.preferences_service

import com.luisma.core.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface IUserPreferencesService {
    suspend fun setUserProfile(userProfile: UserProfile)
    suspend fun getUserProfile(): Flow<UserProfile>
    suspend fun setUserOnboarded()
    suspend fun getUserOnboarded(): Flow<Boolean>
}