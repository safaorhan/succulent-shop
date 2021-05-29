package school.cactus.succulentshop.product.detail

import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.Failure
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.Success
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.TokenExpired
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.UnexpectedError
import school.cactus.succulentshop.product.toProductItem

class ProductDetailRepository {
    suspend fun fetchProductDetail(productId: Int): ProductResult {
        val response = try {
            api.getProductById(productId)
        } catch (ex: Exception) {
            null
        }

        return when (response?.code()) {
            null -> Failure
            200 -> Success(response.body()!!.toProductItem())
            401 -> TokenExpired
            else -> UnexpectedError
        }
    }

    sealed class ProductResult {
        class Success(val product: ProductItem) : ProductResult()
        object TokenExpired : ProductResult()
        object UnexpectedError : ProductResult()
        object Failure : ProductResult()
    }
}