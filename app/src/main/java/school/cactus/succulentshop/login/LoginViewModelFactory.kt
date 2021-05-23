package school.cactus.succulentshop.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import school.cactus.succulentshop.auth.JwtStore

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val store: JwtStore,
    private val repository: LoginRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        LoginViewModel(store, repository) as T
}