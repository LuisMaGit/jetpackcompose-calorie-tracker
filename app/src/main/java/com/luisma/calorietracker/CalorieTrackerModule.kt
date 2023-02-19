package com.luisma.calorietracker

import com.luisma.core_ui.services.GoBackNavigationCommand
import com.luisma.core_ui.services.INavigationCommand
import com.luisma.core_ui.services.NavigationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CalorieTrackerModule {

    @Singleton
    @Provides
    fun navigationService(
        navigationFlow: MutableSharedFlow<INavigationCommand>,
        returnNavigationFlow: MutableSharedFlow<GoBackNavigationCommand>,
    ): NavigationService {
        return NavigationService(
            navigationFlow = navigationFlow,
            returnNavigationFlow = returnNavigationFlow
        )
    }

    @Singleton
    @Provides
    fun provideNavigationFlow(): MutableSharedFlow<INavigationCommand> {
        return MutableSharedFlow()
    }


    @Singleton
    @Provides
    fun providesNavigationReturnCommands(): MutableSharedFlow<GoBackNavigationCommand> {
        return MutableSharedFlow()
    }


}

