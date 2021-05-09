package school.cactus.succulentshop.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

private val client = OkHttpClient.Builder()
    .addNetworkInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://apps.cactus.school")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api: SucculentShopApi = retrofit.create(SucculentShopApi::class.java)