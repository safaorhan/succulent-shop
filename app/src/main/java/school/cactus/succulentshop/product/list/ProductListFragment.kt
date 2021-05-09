package school.cactus.succulentshop.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import school.cactus.succulentshop.R
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.api.product.Product
import school.cactus.succulentshop.databinding.FragmentProductListBinding
import school.cactus.succulentshop.product.toProductItemList

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null

    private val binding get() = _binding!!

    private val adapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.app_name)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        binding.recyclerView.addItemDecoration(ProductDecoration())

        fetchProducts()

        adapter.itemClickListener = {
            val action = ProductListFragmentDirections.openProductDetail(it.id)
            findNavController().navigate(action)
        }
    }


    fun fetchProducts() {
        api.listAllProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                when (response.code()) {
                    200 -> onSuccess(response.body()!!)
                    401 -> onTokenExpired()
                    else -> onUnexpectedError()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Snackbar.make(
                    binding.root, R.string.check_your_connection,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(R.string.retry) {
                    fetchProducts()
                }.show()
            }
        })
    }

    private fun onSuccess(products: List<Product>) {
        adapter.submitList(products.toProductItemList())
    }

    private fun onUnexpectedError() {
        Snackbar.make(
            binding.root, R.string.unexpected_error_occurred,
            BaseTransientBottomBar.LENGTH_LONG
        ).show()
    }

    private fun onTokenExpired() {
        Snackbar.make(
            binding.root, R.string.your_session_is_expired,
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.log_in) {
            navigateToLogin()
        }.show()
    }

    private fun navigateToLogin() = findNavController().navigate(R.id.tokenExpired)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}