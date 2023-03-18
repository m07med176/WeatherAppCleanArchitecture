package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class AlertMapperTest {

    var alertEntityList: MutableList<AlertEntity> = mutableListOf(
        AlertEntity(
            city = "dfsf",
            latitude = 564.53,
            longitude = 565.5,
            endTime = 563289652,
            startTime = 985645,
            startDate = 563289652,
            endDate = 563289652,
        ),
        AlertEntity(
            city = "dfsf",
            latitude = 564.53,
            longitude = 565.5,
            endTime = 563289652,
            startTime = 985645,
            startDate = 563289652,
            endDate = 563289652,
        ),
        AlertEntity(
            city = "dfsf",
            latitude = 564.53,
            longitude = 565.5,
            endTime = 563289652,
            startTime = 985645,
            startDate = 563289652,
            endDate = 563289652,
        ),
    )

    var alertModel: MutableList<AlertModel> = mutableListOf(
        AlertModel(
            city = "dfsf",
            latitude = 564.53,
            longitude = 565.5,
            endTime = "10:25 am",
            startTime = "10:25 am",
            startDate = "20 may",
            endDate = "20 may",
        ),
        AlertModel(
            city = "dfsf",
            latitude = 564.53,
            longitude = 565.5,
            endTime = "10:25 am",
            startTime = "10:25 am",
            startDate = "20 may",
            endDate = "20 may",
        ),
        AlertModel(
            city = "dfsf",
            latitude = 564.53,
            longitude = 565.5,
            endTime = "10:25 am",
            startTime = "10:25 am",
            startDate = "20 may",
            endDate = "20 may",
        ),
    )

    lateinit var alertMapper: AlertMapper

    @Before
    fun handel() {
        alertMapper = AlertMapper()
    }

    @Test
    fun mapFromEntity() {
        // Given
        val item = alertEntityList[0]
        // When
        val result = alertMapper.mapFromEntity(item)
        // Then
        assertThat(result, instanceOf(AlertModel::class.java))
    }

    @Test
    fun entityFromMap() {
        // Given
        val item = alertModel[0]
        // When
        val result = alertMapper.entityFromMap(item)
        // Then
        assertThat(result, instanceOf(AlertEntity::class.java))
    }

    @Test
    fun mapListFromEntityList() {
        // Given : model list
        // When
        val result = alertMapper.mapListFromEntityList(alertEntityList)
        // Then
        assertThat(result[0], instanceOf(AlertModel::class.java))
    }

    @Test
    fun entityListFromMapList() {
        // Given
        // When
        // Then
    }
}