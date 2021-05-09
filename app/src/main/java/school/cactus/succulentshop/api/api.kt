package school.cactus.succulentshop.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import school.cactus.succulentshop.auth.JwtStore

private var _api: SucculentShopApi? = null

// Accessing this will crash if done before calling generate()
val api get() = _api!!

const val BASE_URL = "https://apps.cactus.school"

fun generateApi(context: Context) {
    if (_api != null) return

    val store = JwtStore(context)

    val authInterceptor = AuthInterceptor(store)

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(authInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    _api = retrofit.create(SucculentShopApi::class.java)
}