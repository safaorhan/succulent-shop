package school.cactus.succulentshop.product

data class ProductItem(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String = ""
)
