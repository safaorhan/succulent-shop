package school.cactus.succulentshop.login

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import school.cactus.succulentshop.api.GenericErrorResponse
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.api.login.LoginRequest
import school.cactus.succulentshop.api.login.LoginResponse

class LoginRepository {
    fun sendLoginRequest(
        identifier: String,
        password: String,
        callback: LoginRequestCallback
    ) {
        val request = LoginRequest(identifier, password)

        api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                when (response.code()) {
                    200 -> callback.onSuccess(response.body()!!.jwt)
                    in 400..499 -> callback.onClientError(response.errorBody()!!.errorMessage())
                    else -> callback.onUnexpectedError()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    private fun ResponseBody.errorMessage(): String {
        val errorBody = string()
        val gson: Gson = GsonBuilder().create()
        val loginErrorResponse = gson.fromJson(errorBody, GenericErrorResponse::class.java)
        return loginErrorResponse.message[0].messages[0].message
    }

    interface LoginRequestCallback {
        fun onSuccess(jwt: String)
        fun onClientError(errorMessage: String)
        fun onUnexpectedError()
        fun onFailure()
    }
}