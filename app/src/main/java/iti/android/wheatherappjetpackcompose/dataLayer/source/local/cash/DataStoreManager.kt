package iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.common.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager private constructor(private val context: Context) {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_FILENAME)

        @Volatile
        private var instance: DataStoreManager? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createSingleton(context).also { instance = it }
        }

        private fun createSingleton(context: Context) = DataStoreManager(context)
    }

    // SETTINGS
    private val LANGUAGE_SETTINGS_KEY = stringPreferencesKey(Constants.LANGUAGE_SETTINGS_KEY_NAME)
    private val TEMPERATURE_SETTINGS_KEY =
        stringPreferencesKey(Constants.TEMPERATURE_SETTINGS_KEY_NAME)
    private val WIND_SPEED_SETTINGS_KEY =
        stringPreferencesKey(Constants.WIND_SPEED_SETTINGS_KEY_NAME)
    private val LOCATION_PROVIDER_SETTINGS_KEY =
        stringPreferencesKey(Constants.LOCATION_PROVIDER_SETTINGS_KEY_NAME)
    private val USER_LOCATION_SETTINGS_KEY =
        stringPreferencesKey(Constants.USER_LOCATION_SETTINGS_KEY_NAME)

    fun getSharedSettings(): Flow<Settings> {
        return context.dataStore.data.map { preferences ->
            val strLocation: List<String> =
                (preferences[USER_LOCATION_SETTINGS_KEY] ?: "0.0#0.0").split("#")
            Settings(
                language = enumValueOf<Language>(
                    preferences[LANGUAGE_SETTINGS_KEY] ?: Language.English.name
                ),
                temperature = enumValueOf<Temperature>(
                    preferences[TEMPERATURE_SETTINGS_KEY] ?: Temperature.Celsius.name
                ),
                windSpeed = enumValueOf<WindSpeed>(
                    preferences[WIND_SPEED_SETTINGS_KEY] ?: WindSpeed.Meter.name
                ),
                locationProvider = enumValueOf<LocationProvider>(
                    preferences[LOCATION_PROVIDER_SETTINGS_KEY] ?: LocationProvider.Nothing.name
                ),
                userLocation = LatLng(strLocation[0].toDouble(), strLocation[1].toDouble())
            )
        }
    }

    suspend fun updateTempraturSettings(temperature: Temperature) {
        context.dataStore.edit { settings ->
            settings[TEMPERATURE_SETTINGS_KEY] = temperature.name
        }
    }

    suspend fun updateWindSpeedSettings(windSpeed: WindSpeed) {
        context.dataStore.edit { settings ->
            settings[WIND_SPEED_SETTINGS_KEY] = windSpeed.name
        }
    }

    suspend fun updateLanguageSettings(language: Language) {
        context.dataStore.edit { settings ->
            settings[LANGUAGE_SETTINGS_KEY] = language.name
        }
    }

    suspend fun updateLocationProviderSettings(locationProvider: LocationProvider) {
        context.dataStore.edit { settings ->
            settings[LOCATION_PROVIDER_SETTINGS_KEY] = locationProvider.name
        }
    }

    suspend fun updateUserLocationSettings(latLng: LatLng) {
        context.dataStore.edit { settings ->
            settings[USER_LOCATION_SETTINGS_KEY] = "${latLng.latitude}#${latLng.longitude}"
        }
    }

}