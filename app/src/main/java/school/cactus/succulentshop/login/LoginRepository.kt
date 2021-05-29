package school.cactus.succulentshop.login

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import school.cactus.succulentshop.api.GenericErrorResponse
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.api.login.LoginRequest
import school.cactus.succulentshop.login.LoginRepository.LoginResult.ClientError
import school.cactus.succulentshop.login.LoginRepository.LoginResult.Failure
import school.cactus.succulentshop.login.LoginRepository.LoginResult.Success
import school.cactus.succulentshop.login.LoginRepository.LoginResult.UnexpectedError

class LoginRepository {
    suspend fun sendLoginRequest(
        identifier: String,
        password: String
    ): LoginResult {
        val request = LoginRequest(identifier, password)

        val response = try {
            api.login(request)
        } catch (ex: Exception) {
            null
        }

        return when (response?.code()) {
            null -> Failure
            200 -> Success(response.body()!!.jwt)
            in 400..499 -> ClientError(response.errorBody()!!.errorMessage())
            else -> UnexpectedError
        }
    }

    private fun ResponseBody.errorMessage(): String {
        val errorBody = string()
        val gson: Gson = GsonBuilder().create()
        val loginErrorResponse = gson.fromJson(errorBody, GenericErrorResponse::class.java)
        return loginErrorResponse.message[0].messages[0].message
    }

    sealed class LoginResult {
        class Success(val jwt: String) : LoginResult()
        class ClientError(val errorMessage: String) : LoginResult()
        object UnexpectedError : LoginResult()
        object Failure : LoginResult()
    }
}