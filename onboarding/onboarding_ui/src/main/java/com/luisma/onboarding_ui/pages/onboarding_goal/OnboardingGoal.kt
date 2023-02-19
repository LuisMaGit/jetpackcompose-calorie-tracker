package com.luisma.onboarding_ui.pages.onboarding_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.core.models.GoalUserProfile
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTSpacerV
import com.luisma.onboarding_ui.components.Header
import com.luisma.onboarding_ui.components.Layout
import com.luisma.onboarding_ui.components.ToggleButton

@Composable
fun OnboardingGoal(
    state: OnboardingGoalModel,
    eventDispatcher: (event: OnboardingGoalEvents) -> Unit
) {

    @Composable
    fun getGoalText(goal: GoalUserProfile): String {
        return when (goal) {
            GoalUserProfile.Lose -> stringResource(id = R.string.lose)
            GoalUserProfile.Gain -> stringResource(id = R.string.gain)
            GoalUserProfile.Keep -> stringResource(id = R.string.keep)
            else -> ""
        }
    }

    Layout(
        onClickNext = { eventDispatcher(OnboardingGoalEvents.TapNext) },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(id = R.string.lose_keep_or_gain_weight)
            CTSpacerV.Base2()
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                GoalUserProfile.goals().map { goal ->
                    ToggleButton(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        active = state.goal == goal,
                        text = getGoalText(goal),
                        onClick = { eventDispatcher(OnboardingGoalEvents.SetGoal(goal)) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingGoalPreview() {
    OnboardingGoal(
        state = OnboardingGoalModel(
            goal = GoalUserProfile.Gain
        ),
        eventDispatcher = {}
    )
}