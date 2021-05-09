package school.cactus.succulentshop.api.login

data class LoginRequest(
    val identifier: String,
    val password: String
)