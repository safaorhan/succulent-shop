package school.cactus.succulentshop.infra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import school.cactus.succulentshop.infra.navigation.Navigation
import school.cactus.succulentshop.infra.snackbar.SnackbarState

abstract class BaseViewModel : ViewModel() {
    val navigation = Navigation()

    protected val _snackbarState = MutableLiveData<SnackbarState>()
    val snackbarState: LiveData<SnackbarState> = _snackbarState
}