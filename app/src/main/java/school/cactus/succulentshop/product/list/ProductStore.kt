package school.cactus.succulentshop.product.list

import school.cactus.succulentshop.product.ProductItem

class ProductStore {
    val products = listOf(
        ProductItem(
            1,
            "Cactus plant w/ flowers in a cup",
            "Some description",
            "$20.90"
        ),
        ProductItem(
            2,
            "Small decorative succulent pot hanger",
            "Some description",
            "$10.90"
        ),
        ProductItem(
            3,
            "Medium size succulent plant with white spots",
            "Some description",
            "$4.90"
        ),
        ProductItem(
            4,
            "Set of three cactus plants with rocks, decorated ",
            "Some description",
            "$9.90"
        ),
        ProductItem(
            5,
            "Cactus Plants",
            "Some description",
            "$3.90"
        ),
        ProductItem(6, "Aloe vera pot holder", "Some description", "$2.90"),
        ProductItem(7, "Beautiful set of plants", "Some description", "$2.90"),
        ProductItem(8, "Giant cactus", "Some description", "$5.90"),
    )

    fun findProduct(id: Int) = products.find { it.id == id }!!
}