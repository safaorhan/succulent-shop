package school.cactus.succulentshop.product.list

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.api.product.Product
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.toProductItemList

class ProductListRepository {
    fun fetchProducts(callback: FetchProductsRequestCallback) {
        api.listAllProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                when (response.code()) {
                    200 -> callback.onSuccess(response.body()!!.toProductItemList())
                    401 -> callback.onTokenExpired()
                    else -> callback.onUnexpectedError()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    interface FetchProductsRequestCallback {
        fun onSuccess(products: List<ProductItem>)
        fun onTokenExpired()
        fun onUnexpectedError()
        fun onFailure()
    }
}