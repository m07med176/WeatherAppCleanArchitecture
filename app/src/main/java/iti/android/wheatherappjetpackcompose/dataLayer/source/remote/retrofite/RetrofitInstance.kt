package iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite

import android.content.Context
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.hasNetwork
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance(private val context: Context) {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(cashAndLoggerManager(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NetworkAPI by lazy {
        retrofit.create(NetworkAPI::class.java)
    }


    private fun cashAndLoggerManager(context: Context): OkHttpClient {
        // Logging Retrofit
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // cash size
        val cacheSize = java.lang.Long.valueOf((20 * 1024 * 1024).toLong())
        val myCache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request = chain.request()
                request = if (context.hasNetwork()) request.newBuilder()
                    .header("Cache-Control", "public, max-age=" + Constants.MAX_AGE)
                    .build() else request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + Constants.MAX_STALE
                    )
                    .build()
                chain.proceed(request)
            })
            .addInterceptor(interceptor)
            .build()
    }



}