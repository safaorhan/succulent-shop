package school.cactus.succulentshop.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import school.cactus.succulentshop.R
import school.cactus.succulentshop.infra.BaseViewModel
import school.cactus.succulentshop.infra.snackbar.SnackbarAction
import school.cactus.succulentshop.infra.snackbar.SnackbarState
import school.cactus.succulentshop.product.ProductItem

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

    private fun fetchProducts(): Unit = repository.fetchProducts(
        object : ProductListRepository.FetchProductsRequestCallback {
            override fun onSuccess(products: List<ProductItem>) {
                _products.value = products
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
                            fetchProducts()
                        }
                    )
                )
            }
        })

    private fun navigateToLogin() {
        val directions = ProductListFragmentDirections.tokenExpired()
        navigation.navigate(directions)
    }
}