package school.cactus.succulentshop.product.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.db.db
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.Failure
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.Success
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.TokenExpired
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.UnexpectedError
import school.cactus.succulentshop.product.toProductEntityList
import school.cactus.succulentshop.product.toProductItemList

class ProductListRepository {

    suspend fun fetchProducts(): Flow<ProductListResult> = flow {

        val cachedProducts = db.productDao().getAll()

        if (cachedProducts.isNotEmpty()) {
            emit(Success(cachedProducts.toProductItemList()))
        }

        val response = try {
            api.listAllProducts()
        } catch (ex: Exception) {
            null
        }

        when (response?.code()) {
            null -> emit(Failure)
            200 -> {
                val productItems = response.body()!!.toProductItemList()
                emit(Success(productItems))
                db.productDao().insertAll(productItems.toProductEntityList())
            }
            401 -> emit(TokenExpired)
            else -> emit(UnexpectedError)
        }
    }

    sealed class ProductListResult {
        class Success(val products: List<ProductItem>) : ProductListResult()
        object Failure : ProductListResult()
        object TokenExpired : ProductListResult()
        object UnexpectedError : ProductListResult()
    }
}