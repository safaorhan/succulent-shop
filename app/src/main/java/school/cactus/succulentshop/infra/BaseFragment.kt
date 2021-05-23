package school.cactus.succulentshop.infra

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import school.cactus.succulentshop.infra.navigation.NavigationObserver
import school.cactus.succulentshop.infra.snackbar.SnackbarObserver

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    private val navigationObserver = NavigationObserver()
    private val snackbarObserver = SnackbarObserver()

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationObserver.observe(viewModel.navigation, findNavController(), viewLifecycleOwner)
        snackbarObserver.observe(viewModel.snackbarState, view, viewLifecycleOwner)
    }
}