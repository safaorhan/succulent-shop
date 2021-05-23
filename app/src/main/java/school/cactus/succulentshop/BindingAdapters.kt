package school.cactus.succulentshop

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.list.ProductAdapter
import school.cactus.succulentshop.product.list.ProductDecoration

@BindingAdapter("app:error")
fun TextInputLayout.error(@StringRes errorMessage: Int?) {
    error = errorMessage?.resolveAsString(context)
    isErrorEnabled = errorMessage != null
}

val productAdapter = ProductAdapter()

@BindingAdapter("app:products", "app:itemClickListener")
fun RecyclerView.products(products: List<ProductItem>?, itemClickListener: (ProductItem) -> Unit) {
    adapter = productAdapter

    productAdapter.itemClickListener = itemClickListener

    if (itemDecorationCount == 0) {
        addItemDecoration(ProductDecoration())
    }

    productAdapter.submitList(products.orEmpty())
}