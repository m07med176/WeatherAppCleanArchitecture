package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherDetailsCashMapperTest {

    val weatherModel = WeatherDetailsModel()

    val homeCash = HomeEntity(
        content = weatherModel
    )

    val models = mutableListOf(
        weatherModel, weatherModel, weatherModel
    )

    val entities = mutableListOf(
        homeCash, homeCash, homeCash
    )


    lateinit var weatherDetailsCashMapper: WeatherDetailsCashMapper

    @Before
    fun handler() {
        weatherDetailsCashMapper = WeatherDetailsCashMapper()
    }

    @Test
    fun mapFromEntity() {
        // Given
        val item = entities[0]
        // When
        val result = weatherDetailsCashMapper.mapFromEntity(item)
        // Then
        Assert.assertThat(result, Matchers.instanceOf(WeatherDetailsModel::class.java))
    }

    @Test
    fun entityFromMap() {
        // Given
        val item = models[0]
        // When
        val result = weatherDetailsCashMapper.entityFromMap(item)
        // Then
        Assert.assertThat(result, Matchers.instanceOf(HomeEntity::class.java))
    }

    @Test
    fun mapListFromEntityList() {
        // Given
        val item = entities
        // When
        val result = weatherDetailsCashMapper.mapListFromEntityList(item)
        // Then
        Assert.assertThat(result[0], Matchers.instanceOf(WeatherDetailsModel::class.java))
    }

    @Test
    fun entityListFromMapList() {
        // Given
        val item = models
        // When
        val result = weatherDetailsCashMapper.entityListFromMapList(item)
        // Then
        Assert.assertThat(result[0], Matchers.instanceOf(HomeEntity::class.java))
    }
}