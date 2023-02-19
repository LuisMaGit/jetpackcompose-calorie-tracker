package com.luisma.core_data.di


import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.luisma.core.services.JsonSerializationService
import com.luisma.core.services.database_service.IFoodDataBaseService
import com.luisma.core.services.network_service.IFoodNetworkService
import com.luisma.core.services.network_service.INetworkBaseService
import com.luisma.core.services.preferences_service.IPreferencesService
import com.luisma.core.services.preferences_service.IUserPreferencesService
import com.luisma.core.services.preferences_service.PreferencesContracts
import com.luisma.core_data.Database
import com.luisma.core_data.services.database_service.FoodDataBaseService
import com.luisma.core_data.services.network_service.FoodNetworkService
import com.luisma.core_data.services.network_service.NetworkBaseService
import com.luisma.core_data.services.preferences_service.PreferencesService
import com.luisma.core_data.services.preferences_service.UserProfilePreferenceService
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreDataDI {


    @Singleton
    @Provides
    fun jsonSerializationService(): JsonSerializationService {
        return JsonSerializationService()
    }

    @Singleton
    @Provides
    fun ktorClient(): HttpClient {
        return HttpClient(Android)
    }

    @Singleton
    @Provides
    fun networkBaseService(): INetworkBaseService {
        return NetworkBaseService(ktorClient())
    }

    @Provides
    fun foodNetworkService(
        networkBaseService: INetworkBaseService,
    ): IFoodNetworkService {
        return FoodNetworkService(
            client = networkBaseService,
            jsonSerializationService = jsonSerializationService()
        )
    }

    @Provides
    @Singleton
    fun sqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = Database.Schema,
            context = app,
            name = "FoodTracker.db"
        )
    }

    @Provides
    @Singleton
    fun foodDataBaseService(
        app: Application,
    ): IFoodDataBaseService {
        val sqlDriver = AndroidSqliteDriver(
            schema = Database.Schema,
            context = app,
            name = "FoodTracker.db"
        )
        val queries = Database(sqlDriver).foodTrackerTableQueries
        return FoodDataBaseService(
            queries = queries,
            jsonSerializationService = jsonSerializationService()
        )
    }


    @Singleton
    @Provides
    fun preferencesService(
        @ApplicationContext context: Context,
    ): IPreferencesService {
        val dataStore = PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(
                    PreferencesContracts.STORE_NAME
                )
            }
        )
//      return FakePreferencesService()
        return PreferencesService(
            dataStore = dataStore,
        )
    }


    @Singleton
    @Provides
    fun userPreferencesService(
        jsonSerializationService: JsonSerializationService,
        preferenceService: IPreferencesService
    ): IUserPreferencesService {
        return UserProfilePreferenceService(
            jsonSerializationService = jsonSerializationService,
            preferencesService =preferenceService
        )
    }
}