import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import iti.android.wheatherappjetpackcompose.MainCoroutineRule
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.NetworkAPI
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.RetrofitInstance
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.Units
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NetworkAPITest {

    private lateinit var retrofitInstance: RetrofitInstance
    private lateinit var api: NetworkAPI

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun initRetrofit() {
        val context = InstrumentationRegistry.getInstrumentation().context
        retrofitInstance = RetrofitInstance(context)
        api = retrofitInstance.api
    }


    @Test
    fun getWeatherDetails_requestAPI_isSuccessful() = runBlocking {
        // When
        val response = async {
            api.getWeatherDetails(
                // Given
                longitude = 0.556,
                latitude = 0.5654,
                units = Units.imperial.name,
                language = Constants.ENGLISH
            )
        }
        // Then
        MatcherAssert.assertThat(256532, Is.`is`(256532))
        MatcherAssert.assertThat(response.await().body(), CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.await().errorBody(), CoreMatchers.nullValue())
    }

    @Test
    fun getWeatherDetails_requestAPINoKey_unAuthorized() = runBlocking {
        // When
        val response = async {
            api.getWeatherDetails(
                // Given
                longitude = 0.556,
                latitude = 0.5654,
                units = Units.imperial.name,
                language = Constants.ENGLISH,
                apiKey = ""
            )
        }
        // Then
        MatcherAssert.assertThat(response.await().code(), Is.`is`(401))
        MatcherAssert.assertThat(response.await().body(), CoreMatchers.nullValue())
        MatcherAssert.assertThat(response.await().errorBody(), CoreMatchers.notNullValue())
    }
}