package school.cactus.succulentshop

interface Validator {
    fun validate(field: String): Int?
}