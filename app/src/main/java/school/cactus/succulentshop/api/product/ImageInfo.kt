package school.cactus.succulentshop.api.product

data class ImageInfo(
    val id: Long,
    val name: String,
    val width: Long,
    val height: Long,
    val formats: ImageFormats,
)