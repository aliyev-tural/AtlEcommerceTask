package com.example.ecommercetask.presentation.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.ecommercetask.MainActivityViewModel
import com.example.ecommercetask.R
import com.example.ecommercetask.databinding.FragmentProductBinding
import com.example.ecommercetask.presentation.base.BaseFragment
import com.example.ecommercetask.presentation.ShoppingCartSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentProductBinding>(
    FragmentProductBinding::inflate
) {

    private val cartSharedViewModel: ShoppingCartSharedViewModel by hiltNavGraphViewModels(R.id.main_nav_graph)
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        arguments?.getString("PRODUCT_ID")?.let { cartSharedViewModel.getProduct(it) }

        viewLifecycleOwner.lifecycleScope.launch {
            cartSharedViewModel.uiState.collect { state ->
                binding.progressBar3.isVisible = state.isLoading

                if (state.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }

                state.product?.let { product ->
                    Log.d("PRODUCT__MY", product.toString())
                    binding.apply {
                        imgProduct.load(product.image)
                        txtproductName.text = product.title
                        txtPrice.text = "${product.price}$"
                        txtProductDetails.text = product.description
                    }
                }
            }
        }

        binding.btnAddToCart.setOnClickListener {
            cartSharedViewModel.uiState.value.product?.id?.let { productId ->
                mainViewModel.currentUser.value?.uid?.let { userId ->
                    cartSharedViewModel.addProductToShoppingCart(productId, userId)
                }
            }
        }

        return binding.root
    }
}
