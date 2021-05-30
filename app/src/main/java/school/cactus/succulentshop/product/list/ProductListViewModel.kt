package school.cactus.succulentshop.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import school.cactus.succulentshop.R
import school.cactus.succulentshop.infra.BaseViewModel
import school.cactus.succulentshop.infra.snackbar.SnackbarAction
import school.cactus.succulentshop.infra.snackbar.SnackbarState
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.Failure
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.Success
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.TokenExpired
import school.cactus.succulentshop.product.list.ProductListRepository.ProductListResult.UnexpectedError

class ProductListViewModel(private val repository: ProductListRepository) : BaseViewModel() {
    private val _products = MutableLiveData<List<ProductItem>>()
    val products: LiveData<List<ProductItem>> = _products

    val itemClickListener: (ProductItem) -> Unit = {
        val action = ProductListFragmentDirections.openProductDetail(it.id)
        navigation.navigate(action)
    }

    init {
        fetchProducts()
    }

    private fun fetchProducts() = viewModelScope.launch {
        when (val result = repository.fetchProducts()) {
            is Success -> onSuccess(result.products)
            TokenExpired -> onTokenExpired()
            UnexpectedError -> onUnexpectedError()
            Failure -> onFailure()
        }
    }

    private fun onSuccess(products: List<ProductItem>) {
        _products.value = products
    }

    private fun onTokenExpired() {
        _snackbarState.value = SnackbarState(
            errorRes = R.string.your_session_is_expired,
            duration = Snackbar.LENGTH_INDEFINITE,
            action = SnackbarAction(
                text = R.string.log_in,
                action = {
                    navigateToLogin()
                }
            )
        )
    }

    private fun onUnexpectedError() {
        _snackbarState.value = SnackbarState(
            errorRes = R.string.unexpected_error_occurred,
            duration = Snackbar.LENGTH_LONG,
        )
    }

    private fun onFailure() {
        _snackbarState.value = SnackbarState(
            errorRes = R.string.check_your_connection,
            duration = Snackbar.LENGTH_INDEFINITE,
            action = SnackbarAction(
                text = R.string.retry,
                action = {
                    fetchProducts()
                }
            )
        )
    }

    private fun navigateToLogin() {
        val directions = ProductListFragmentDirections.tokenExpired()
        navigation.navigate(directions)
    }
}