package school.cactus.succulentshop.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import school.cactus.succulentshop.R
import school.cactus.succulentshop.databinding.FragmentProductListBinding
import school.cactus.succulentshop.infra.BaseFragment

class ProductListFragment : BaseFragment() {
    private var _binding: FragmentProductListBinding? = null

    private val binding get() = _binding!!

    override val viewModel: ProductListViewModel by viewModels {
        ProductListViewModelFactory(ProductListRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.app_name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}