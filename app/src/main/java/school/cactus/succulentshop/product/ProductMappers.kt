package school.cactus.succulentshop.product

import school.cactus.succulentshop.api.BASE_URL
import school.cactus.succulentshop.api.product.Product

fun List<Product>.toProductItemList() = map {
    it.toProductItem()
}

fun Product.toProductItem() = ProductItem(
    id = id,
    title = title,
    description = description,
    price = price.formatPrice(),
    imageUrl = BASE_URL + image.formats.small.url,
    highResImageUrl = BASE_URL + image.formats.medium.url
)

private fun Double.formatPrice() = String.format("$%.2f", this)