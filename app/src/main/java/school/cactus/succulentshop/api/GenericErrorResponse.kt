package school.cactus.succulentshop.api

data class GenericErrorResponse(
    val message: List<OuterMessage>
)

data class OuterMessage(
    val messages: List<InnerMessage>
)

data class InnerMessage(
    val id: String,
    val message: String
)