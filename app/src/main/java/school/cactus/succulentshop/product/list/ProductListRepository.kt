package school.cactus.succulentshop.product.list

import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.Failure
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.Success
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.TokenExpired
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.UnexpectedError
import school.cactus.succulentshop.product.toProductItemList

class ProductListRepository {
    suspend fun fetchProducts(): ProductListResult {
        val response = try {
            api.listAllProducts()
        } catch (ex: Exception) {
            null
        }

        return when (response?.code()) {
            null -> Failure
            200 -> Success(response.body()!!.toProductItemList())
            401 -> TokenExpired
            else -> UnexpectedError
        }
    }

    sealed class ProductListResult {
        class Success(val products: List<ProductItem>) : ProductListResult()
        object Failure : ProductListResult()
        object TokenExpired : ProductListResult()
        object UnexpectedError : ProductListResult()
    }
}