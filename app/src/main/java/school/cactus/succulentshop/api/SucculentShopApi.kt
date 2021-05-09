package school.cactus.succulentshop.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import school.cactus.succulentshop.api.login.LoginRequest
import school.cactus.succulentshop.api.login.LoginResponse
import school.cactus.succulentshop.api.product.Product

interface SucculentShopApi {
    @POST("/auth/local")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("/products")
    fun listAllProducts(): Call<List<Product>>
}