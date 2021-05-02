package school.cactus.succulentshop.product.list

import school.cactus.succulentshop.R

class ProductStore {
    val products = listOf(
        Product(
            1,
            "Cactus plant w/ flowers in a cup",
            "Some description",
            "$20.90",
            R.drawable.plant1
        ),
        Product(
            2,
            "Small decorative succulent pot hanger",
            "Some description",
            "$10.90",
            R.drawable.plant2
        ),
        Product(
            3,
            "Medium size succulent plant with white spots",
            "Some description",
            "$4.90",
            R.drawable.plant3
        ),
        Product(
            4,
            "Set of three cactus plants with rocks, decorated ",
            "Some description",
            "$9.90",
            R.drawable.plant4
        ),
        Product(
            5,
            "Cactus Plants",
            "Some description",
            "$3.90",
            R.drawable.plant5
        ),
        Product(6, "Aloe vera pot holder", "Some description", "$2.90", R.drawable.plant6),
        Product(7, "Beautiful set of plants", "Some description", "$2.90", R.drawable.plant7),
        Product(8, "Giant cactus", "Some description", "$5.90", R.drawable.plant8),
    )

    fun findProduct(id: Int) = products.find { it.id == id }!!
}