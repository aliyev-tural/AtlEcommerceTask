package com.example.ecommercetask.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.ecommercetask.R
import com.example.ecommercetask.data.remote.firestore.Product
import com.example.ecommercetask.databinding.FragmentHomeBinding
import com.example.ecommercetask.databinding.RvItemProductBinding
import com.example.ecommercetask.presentation.base.BaseFragment
import com.example.ecommercetask.presentation.base.GenericRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
){

    val homeViewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        val productAdapter = GenericRvAdapter<Product,RvItemProductBinding>(
            RvItemProductBinding::inflate
        ){
            binding,product,position,_ ->
            binding.apply {
                imgProduct.load(product.image)
                txtproductName.text = product.title
                txtPrice.text = product.price.toString()


                root.setOnClickListener{
                    val bundle = bundleOf()
                    bundle.putString("PRODUCT_ID",product.id.toString())
                    findNavController().navigate(R.id.productFragment,bundle)
                }
            }

        }
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = productAdapter
        }

        homeViewModel.uiState.onEach { state ->
            binding.progressBar2.isVisible = state.isLoading
            val error = state.error.takeIf {  it.isNotEmpty() }
            Log.d("DATABASE_ERROR","$error")


            if(state.products!=null){
                productAdapter.sendListToAdapter(state.products)
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return  binding.root
    }
}