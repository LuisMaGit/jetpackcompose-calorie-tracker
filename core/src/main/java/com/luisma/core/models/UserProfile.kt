package com.luisma.core.models

data class UserProfile(
    val gender: GenderUserProfile = GenderUserProfile.Unknown,
    val goal: GoalUserProfile = GoalUserProfile.Unknown,
    val activityLevel: ActivityLevelUserProfile = ActivityLevelUserProfile.Unknown,
    val age: Int = 0,
    val weight: Float = 0f,
    val height: Int = 0,
    val carbRatio: Int = 0,
    val proteinRatio: Int = 0,
    val fatRatio: Int = 0,
) {
    fun carbRatioFloat(): Float = carbRatio / 100f
    fun proteinRatioFloat(): Float = proteinRatio / 100f
    fun fatRatioFloat(): Float = fatRatio / 100f
}

enum class GenderUserProfile(val value: String) {
    Male("male"),
    Female("female"),
    Unknown("");

    companion object {
        fun getType(value: String): GenderUserProfile {
            return enumValues<GenderUserProfile>().find {
                it.value == value
            }.let {
                it ?: Unknown
            }
        }
    }
}

enum class ActivityLevelUserProfile(val value: String) {
    Low("low"),
    Medium("medium"),
    High("high"),
    Unknown("");

    companion object {
        fun getType(value: String): ActivityLevelUserProfile {
            return enumValues<ActivityLevelUserProfile>().find {
                it.value == value
            }.let {
                it ?: Unknown
            }
        }

        fun activities(): List<ActivityLevelUserProfile> {
            return values().filter { it != Unknown }
        }
    }

}

enum class GoalUserProfile(val value: String) {
    Lose("lose"),
    Keep("keep"),
    Gain("gain"),
    Unknown("");

    companion object {
        fun getType(value: String): GoalUserProfile {
            return enumValues<GoalUserProfile>().find {
                it.value == value
            }.let {
                it ?: Unknown
            }
        }

        fun goals(): List<GoalUserProfile> {
            return values().filter { it != Unknown }
        }
    }
}


