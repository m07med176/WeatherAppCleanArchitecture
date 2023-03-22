package iti.android.wheatherappjetpackcompose.domainLayer.models

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FavPlacesMapperTest {

    val models = mutableListOf(
        FavPlacesModel(
            city = "klhkj",
            location = LatLng(65.5, 5.56)
        ),
        FavPlacesModel(
            city = "klhkj",
            location = LatLng(65.5, 5.56)
        ),
        FavPlacesModel(
            city = "klhkj",
            location = LatLng(65.5, 5.56)
        ),

        )

    val entities = mutableListOf(
        FavoriteEntity(
            city = "655",
            latitude = 56.5,
            longitude = 74.5,
            id = 5
        ),
        FavoriteEntity(
            city = "655",
            latitude = 56.5,
            longitude = 74.5,
            id = 5
        ),
        FavoriteEntity(
            city = "655",
            latitude = 56.5,
            longitude = 74.5,
            id = 5
        ),
        FavoriteEntity(
            city = "655",
            latitude = 56.5,
            longitude = 74.5,
            id = 5
        ),

        )
    lateinit var favPlacesMapper: FavPlacesMapper

    @Before
    fun handler() {
        favPlacesMapper = FavPlacesMapper()

    }

    @Test
    fun mapFromEntity() {
        // Given
        val item = entities[0]
        // When
        val result = favPlacesMapper.mapFromEntity(item)
        // Then
        Assert.assertThat(result, Matchers.instanceOf(FavPlacesModel::class.java))
    }

    @Test
    fun entityFromMap() {
        // Given
        val item = models[0]
        // When
        val result = favPlacesMapper.entityFromMap(item)
        // Then
        Assert.assertThat(result, Matchers.instanceOf(FavoriteEntity::class.java))
    }

    @Test
    fun mapListFromEntityList() {
        // Given
        val item = entities
        // When
        val result = favPlacesMapper.mapListFromEntityList(item)
        // Then
        Assert.assertThat(result[0], Matchers.instanceOf(FavPlacesModel::class.java))
    }

    @Test
    fun entityListFromMapList() {
        // Given
        val item = models
        // When
        val result = favPlacesMapper.entityListFromMapList(item)
        // Then
        Assert.assertThat(result[0], Matchers.instanceOf(FavoriteEntity::class.java))
    }
}