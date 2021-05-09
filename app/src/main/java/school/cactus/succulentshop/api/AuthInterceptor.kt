package school.cactus.succulentshop.api

import okhttp3.Interceptor
import okhttp3.Response
import school.cactus.succulentshop.auth.JwtStore

class AuthInterceptor(private val store: JwtStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        if (chain.request().url.encodedPathSegments[0] != "auth") {
            requestBuilder.addHeader("Authorization", "Bearer ${store.loadJwt()}")
        }

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}