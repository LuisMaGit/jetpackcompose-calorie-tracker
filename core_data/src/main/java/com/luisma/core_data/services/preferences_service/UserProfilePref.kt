package com.luisma.core_data.services.preferences_service

import com.luisma.core.models.ActivityLevelUserProfile
import com.luisma.core.models.GenderUserProfile
import com.luisma.core.models.GoalUserProfile
import com.luisma.core.models.UserProfile
import com.luisma.core.services.JsonSerializationService
import kotlinx.serialization.Serializable

@Serializable
data class UserProfilePref(
    val gender: String,
    val goal: String,
    val activityLevel: String,
    val age: Int,
    val weight: Float,
    val height: Int,
    val carbRatio: Int,
    val proteinRatio: Int,
    val fatRatio: Int,
) {
    companion object {
        fun fromJson(
            data: String,
            json: JsonSerializationService
        ): UserProfilePref {
            return json.decodeFromString(data)
        }

        fun fromCore(core: UserProfile): UserProfilePref {
            return UserProfilePref(
                gender = core.gender.value,
                goal = core.goal.value,
                activityLevel = core.activityLevel.value,
                age = core.age,
                weight = core.weight,
                height = core.height,
                carbRatio = core.carbRatio,
                proteinRatio = core.proteinRatio,
                fatRatio = core.fatRatio,
            )
        }

    }

    fun toCore(): UserProfile {
        return UserProfile(
            gender = GenderUserProfile.getType(gender),
            goal = GoalUserProfile.getType(goal),
            activityLevel = ActivityLevelUserProfile.getType(activityLevel),
            age = age,
            weight = weight,
            height = height,
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio,
        )
    }

    fun toJson(
        json: JsonSerializationService
    ): String {
        return json.encodeToString(this)
    }
}