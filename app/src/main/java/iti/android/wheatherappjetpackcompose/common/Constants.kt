package iti.android.wheatherappjetpackcompose.common

object Constants {

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "698e3af38374a53ef1b6cc63e337b71c"

    // Network cash
    const val MAX_AGE = 5
    const val MAX_STALE = 60 * 60 * 24 * 7

    // Datastore
    const val DATASTORE_FILENAME = "datastore"
    const val USER_LOCATION_SETTINGS_KEY_NAME = "USER_LOCATION_SETTINGS_KEY_NAME"
    const val LANGUAGE_SETTINGS_KEY_NAME = "LANGUAGE_SETTINGS_KEY_NAME"
    const val TEMPERATURE_SETTINGS_KEY_NAME = "TEMPERATURE_SETTINGS_KEY_NAME"
    const val WIND_SPEED_SETTINGS_KEY_NAME = "WIND_SPEED_SETTINGS_KEY_NAME"
    const val LOCATION_PROVIDER_SETTINGS_KEY_NAME = "LOCATION_PROVIDER_SETTINGS_KEY_NAME"


    // Language Code
    const val ARABIC = "ar"
    const val ENGLISH = "en"
}