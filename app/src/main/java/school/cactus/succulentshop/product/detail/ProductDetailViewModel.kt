package school.cactus.succulentshop.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import school.cactus.succulentshop.R
import school.cactus.succulentshop.infra.BaseViewModel
import school.cactus.succulentshop.infra.snackbar.SnackbarAction
import school.cactus.succulentshop.infra.snackbar.SnackbarState
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.detail.ProductDetailRepository.FetchProductDetailRequestCallback
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

    fun fetchProduct() {
        repository.fetchProductDetail(productId, object : FetchProductDetailRequestCallback {
            override fun onSuccess(product: ProductItem) {
                _product.value = product
            }

            override fun onTokenExpired() {
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

            override fun onUnexpectedError() {
                _snackbarState.value = SnackbarState(
                    errorRes = R.string.unexpected_error_occurred,
                    duration = Snackbar.LENGTH_LONG,
                )
            }

            override fun onFailure() {
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
        })
    }

    private fun navigateToLogin() {
        val directions = ProductListFragmentDirections.tokenExpired()
        navigation.navigate(directions)
    }
}