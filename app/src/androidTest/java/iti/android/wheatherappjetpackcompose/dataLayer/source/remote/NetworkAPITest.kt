package iti.android.wheatherappjetpackcompose.dataLayer.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import iti.android.wheatherappjetpackcompose.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
        api = retrofitInstance.retrofit.create(NetworkAPI::class.java)
    }


    @Test
    fun getWeatherDetails() = mainCoroutineRule.runBlockingTest {
//        val response = async {
//            api.getWeatherDetails(
//                longitude = 0.556,
//                latitude = 0.5654,
//            )
//        }
//
//        MatcherAssert.assertThat(256532, Is.`is`(256532))
//        MatcherAssert.assertThat(response.await().body(), CoreMatchers.notNullValue())

    }
}