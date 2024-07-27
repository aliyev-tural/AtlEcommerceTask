package com.example.ecommercetask.presentation.shoppingCart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.ecommercetask.R
import com.example.ecommercetask.databinding.FragmentShoppingCartBinding
import com.example.ecommercetask.presentation.ShoppingCartSharedViewModel
import com.example.ecommercetask.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingCartFragment(): BaseFragment<FragmentShoppingCartBinding>(
    FragmentShoppingCartBinding::inflate
) {

    val cartSharedViewModel: ShoppingCartSharedViewModel by hiltNavGraphViewModels(R.id.main_nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}