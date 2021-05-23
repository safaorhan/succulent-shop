package school.cactus.succulentshop.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ProductDetailViewModelFactory(
    private val productId: Int,
    private val repository: ProductDetailRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ProductDetailViewModel(productId, repository) as T
}