package school.cactus.succulentshop.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import school.cactus.succulentshop.R
import school.cactus.succulentshop.infra.BaseViewModel
import school.cactus.succulentshop.infra.snackbar.SnackbarAction
import school.cactus.succulentshop.infra.snackbar.SnackbarState
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.Failure
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.Success
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.TokenExpired
import school.cactus.succulentshop.product.detail.ProductDetailRepository.ProductResult.UnexpectedError
import school.cactus.succulentshop.product.list.ProductListFragmentDirections

class ProductDetailViewModel(
    private val productId: Int,
    private val repository: ProductDetailRepository
) : BaseViewModel() {

    private val _product = MutableLiveData<ProductItem>()
    val product: LiveData<ProductItem> = _product

    init {
        fetchProduct()
    }

    private fun fetchProduct() = viewModelScope.launch {
        repository.fetchProductDetail(productId).collect {
            when (it) {
                is Success -> onSuccess(it.product)
                TokenExpired -> onTokenExpired()
                UnexpectedError -> onUnexpectedError()
                Failure -> onFailure()
            }
        }
    }

    private fun onSuccess(product: ProductItem) {
        _product.value = product
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
                    fetchProduct()
                }
            )
        )
    }

    private fun navigateToLogin() {
        val directions = ProductListFragmentDirections.tokenExpired()
        navigation.navigate(directions)
    }
}