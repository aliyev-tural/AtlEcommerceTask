package com.example.ecommercetask.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ecommercetask.R
import com.example.ecommercetask.databinding.FragmentSignInUpOptionBinding
import com.example.ecommercetask.presentation.base.BaseFragment

class SignInUpOptionFragment: BaseFragment<FragmentSignInUpOptionBinding>(
    FragmentSignInUpOptionBinding::inflate
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)


        binding.signInBtnOption.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

        binding.signUpBtnOption.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        return binding.root

    }
}