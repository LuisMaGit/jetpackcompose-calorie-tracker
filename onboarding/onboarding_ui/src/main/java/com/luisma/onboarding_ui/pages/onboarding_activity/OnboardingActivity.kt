package com.luisma.onboarding_ui.pages.onboarding_activity

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
import com.luisma.core.models.ActivityLevelUserProfile
import com.luisma.core_ui.R
import com.luisma.onboarding_ui.components.Header
import com.luisma.onboarding_ui.components.Layout
import com.luisma.onboarding_ui.components.ToggleButton

@Composable
fun OnboardingActivity(
    eventDispatcher: (event: OnboardingActivityEvents) -> Unit,
    state: OnboardingActivityModel
) {

    @Composable
    fun buttonText(activity: ActivityLevelUserProfile): String {
        return when (activity) {
            ActivityLevelUserProfile.Low -> stringResource(R.string.low)
            ActivityLevelUserProfile.Medium -> stringResource(R.string.medium)
            ActivityLevelUserProfile.High -> stringResource(R.string.high)
            else -> stringResource(R.string.high)
        }
    }

    Layout(
        onClickNext = { eventDispatcher(OnboardingActivityEvents.TapNext) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(id = R.string.whats_your_activity_level)
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                ActivityLevelUserProfile.activities().map { activity ->
                    ToggleButton(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        active = activity == state.activity,
                        text = buttonText(activity),
                        onClick = {
                            eventDispatcher(
                                OnboardingActivityEvents.SetActivity(activity)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingActivityPreview() {
    OnboardingActivity(
        state = OnboardingActivityModel(
            activity = ActivityLevelUserProfile.Low
        ),
        eventDispatcher = {}
    )
}