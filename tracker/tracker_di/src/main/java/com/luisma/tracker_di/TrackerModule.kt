package com.luisma.tracker_di

import com.luisma.tracker_domain.use_cases.MealCalculatorUseCase
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboardModel
import com.luisma.tracker_ui.pages.tracker_search_food.TrackerSearchFoodModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TrackerModule {

    @Provides
    fun providesMealCalculatorUserCase(): MealCalculatorUseCase {
        return MealCalculatorUseCase()
    }

    @Provides
    fun providesTrackerSearchFoodState(): TrackerSearchFoodModel {
        return TrackerSearchFoodModel.initial()
    }

    @Provides
    fun providesTrackerDashboardState(): TrackerDashboardModel {
        return TrackerDashboardModel.initial()
    }
}

