package school.cactus.succulentshop.api.product

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: ImageInfo
)
