package school.cactus.succulentshop.infra.snackbar

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import school.cactus.succulentshop.resolveAsString

class SnackbarObserver {
    private var snackbar: Snackbar? = null

    fun observe(
        snackbarState: LiveData<SnackbarState>,
        view: View,
        lifecycleOwner: LifecycleOwner
    ) =
        snackbarState.observe(lifecycleOwner) { state ->
            if (state != null) {
                val message = state.errorRes?.resolveAsString(view.context) ?: state.error
                snackbar = Snackbar.make(view, message!!, state.duration)

                if (state.action != null) {
                    snackbar!!.setAction(state.action.text) {
                        state.action.action()
                    }
                }

                snackbar!!.show()
            } else {
                snackbar?.dismiss()
            }
        }
}