package school.cactus.succulentshop.product.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import school.cactus.succulentshop.R

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    val products = listOf(
        Product("Cactus plant w/ flowers in a cup", "$12.90"),
        Product("Small decorative succulent pot hanger", "$12.90"),
        Product("Medium size succulent plant with white spots", "$2.90"),
        Product("Set of three cactus plants with rocks", "$3.90"),
        Product("Small decorative succulent pot hanger", "$4.90"),
        Product("Small decorative succulent pot hanger", "$5.90"),
        Product("Small decorative succulent pot hanger", "$2.90"),
        Product("Small decorative succulent pot hanger", "$12.90"),
        Product("Small decorative succulent pot hanger", "$10.90"),
        Product("Small decorative succulent pot hanger", "$10.90"),
        Product("Small decorative succulent pot hanger", "$2.90")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.titleText.text = products[position].title
        holder.priceText.text = products[position].price

        holder.imageView.layoutParams = holder.imageView.layoutParams.apply {
            height = (1..5).random() * 100
        }
    }

    override fun getItemCount() = products.size

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val priceText: TextView = itemView.findViewById(R.id.priceText)
    }
}