package school.cactus.succulentshop.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import school.cactus.succulentshop.R
import school.cactus.succulentshop.auth.JwtStore
import school.cactus.succulentshop.infra.BaseViewModel
import school.cactus.succulentshop.infra.snackbar.SnackbarAction
import school.cactus.succulentshop.infra.snackbar.SnackbarState
import school.cactus.succulentshop.login.LoginRepository.LoginRequestCallback
import school.cactus.succulentshop.login.validation.IdentifierValidator
import school.cactus.succulentshop.login.validation.PasswordValidator

class LoginViewModel(
    private val store: JwtStore,
    private val repository: LoginRepository
) : BaseViewModel() {

    private val identifierValidator = IdentifierValidator()
    private val passwordValidator = PasswordValidator()

    val identifier = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _identifierErrorMessage = MutableLiveData<Int>()
    private val _passwordErrorMessage = MutableLiveData<Int>()

    val identifierErrorMessage: LiveData<Int> = _identifierErrorMessage
    val passwordErrorMessage: LiveData<Int> = _passwordErrorMessage

    fun onLoginButtonClick() {
        if (isIdentifierValid() and isPasswordValid()) {
            repository.sendLoginRequest(
                identifier.value.orEmpty(),
                password.value.orEmpty(),
                object : LoginRequestCallback {
                    override fun onSuccess(jwt: String) {
                        store.saveJwt(jwt)

                        val directions = LoginFragmentDirections.loginSuccessful()
                        navigation.navigate(directions)
                    }

                    override fun onClientError(errorMessage: String) {
                        _snackbarState.value = SnackbarState(
                            error = errorMessage,
                            duration = LENGTH_LONG
                        )
                    }

                    override fun onUnexpectedError() {
                        _snackbarState.value = SnackbarState(
                            errorRes = R.string.unexpected_error_occurred,
                            duration = LENGTH_LONG
                        )
                    }

                    override fun onFailure() {
                        _snackbarState.value = SnackbarState(
                            errorRes = R.string.check_your_connection,
                            duration = LENGTH_INDEFINITE,
                            action = SnackbarAction(
                                text = R.string.retry,
                                action = {
                                    onLoginButtonClick()
                                }
                            )
                        )
                    }
                })
        }
    }

    private fun isIdentifierValid(): Boolean {
        _identifierErrorMessage.value = identifierValidator.validate(identifier.value!!)
        return _identifierErrorMessage.value == null
    }

    private fun isPasswordValid(): Boolean {
        _passwordErrorMessage.value = passwordValidator.validate(password.value!!)
        return _passwordErrorMessage.value == null
    }
}