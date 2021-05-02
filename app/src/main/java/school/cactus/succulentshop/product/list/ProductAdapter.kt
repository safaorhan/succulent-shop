package school.cactus.succulentshop.product.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import school.cactus.succulentshop.databinding.ItemProductBinding
import school.cactus.succulentshop.product.list.ProductAdapter.ProductHolder

class ProductAdapter : ListAdapter<Product, ProductHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.titleText.text = product.title
            binding.priceText.text = product.price
            binding.imageView.setImageResource(product.imageUrl)

            Glide.with(binding.root.context)
                .load(product.imageUrl)
                .override(512)
                .into(binding.imageView)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
        }
    }
}