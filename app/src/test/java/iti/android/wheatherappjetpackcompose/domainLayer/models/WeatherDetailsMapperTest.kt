package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Current
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherDetailsMapperTest {

    val weatherSuccessResponse = WeatherSuccessResponse(
        lat = 0.256532,
        lon = 0.065535,
        timezone = "56352232",
        timezone_offset = 55,
        current = Current(
            clouds = 54.toDouble(),
            dew_point = 0.5455,
            dt = 51.toDouble(),
            feels_like = 5.24,
            humidity = 5635.toDouble(),
            pressure = 545.toDouble(),
            sunrise = 54645.toDouble(),
            sunset = 564.toDouble(),
            temp = 0.224,
            uvi = 0.2454,
            visibility = 45.toDouble(),
            weather = emptyList(),
            wind_deg = 554.toDouble(),
            wind_gust = 0.25,
            wind_speed = 564.toDouble()
        )

    )

    val weatherModel = WeatherDetailsModel()


    val entities = mutableListOf(
        weatherSuccessResponse, weatherSuccessResponse, weatherSuccessResponse
    )

    val models = mutableListOf(
        weatherModel, weatherModel, weatherModel
    )

    lateinit var weatherDetailsMapper: WeatherDetailsMapper

    @Before
    fun handler() {
        weatherDetailsMapper = WeatherDetailsMapper()
    }

    @Test
    fun mapFromEntity() {
        // Given
        val item = entities[0]
        // When
        val result = weatherDetailsMapper.mapFromEntity(item)
        // Then
        Assert.assertThat(result, Matchers.instanceOf(WeatherDetailsModel::class.java))
    }

    @Test
    fun entityFromMap() {
        // Given
        val item = models[0]
        // When
        val result = weatherDetailsMapper.entityFromMap(item)
        // Then
        Assert.assertThat(result, Matchers.instanceOf(WeatherSuccessResponse::class.java))
    }

    @Test
    fun mapListFromEntityList() {
        // Given
        val item = entities
        // When
        val result = weatherDetailsMapper.mapListFromEntityList(item)
        // Then
        Assert.assertThat(result[0], Matchers.instanceOf(WeatherDetailsModel::class.java))
    }

    @Test
    fun entityListFromMapList() {
        // Given
        val item = models
        // When
        val result = weatherDetailsMapper.entityListFromMapList(item)
        // Then
        Assert.assertThat(result[0], Matchers.instanceOf(WeatherSuccessResponse::class.java))
    }
}