package com.luisma.core.di

import com.luisma.core.DefaultDispatcher
import com.luisma.core.IoDispatcher
import com.luisma.core.MainDispatcher
import com.luisma.core.services.StringHelperService
import com.luisma.core.services.TimeHelperService
import com.luisma.core.services.pagination_service.PaginationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoreDI {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    fun stringHelperService(): StringHelperService {
        return StringHelperService
    }

    @Provides
    fun timeHelperService(): TimeHelperService {
        return TimeHelperService()
    }

    @Provides
    fun paginationService(): PaginationService {
        return PaginationService()
    }
}