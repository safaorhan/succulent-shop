package school.cactus.succulentshop.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import school.cactus.succulentshop.R
import school.cactus.succulentshop.auth.JwtStore
import school.cactus.succulentshop.databinding.FragmentLoginBinding
import school.cactus.succulentshop.resolveAsString

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(JwtStore(requireContext()))
    }

    var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            logInButton.setOnClickListener {
                this@LoginFragment.viewModel.onLoginButtonClick()
            }
        }

        observeSnackbarState()

        requireActivity().title = getString(R.string.log_in)
    }

    private fun observeSnackbarState() =
        viewModel.snackbarState.observe(viewLifecycleOwner) { state ->
            if (state != null) {
                val message = state.errorRes?.resolveAsString(requireContext()) ?: state.error
                snackbar = Snackbar.make(binding.root, message!!, state.duration)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}