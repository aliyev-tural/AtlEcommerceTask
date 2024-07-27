package com.example.ecommercetask.presentation.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommercetask.R
import com.example.ecommercetask.databinding.FragmentSignUpBinding
import com.example.ecommercetask.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
@AndroidEntryPoint
class SignUpFragment:BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {

    val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val progressBar = binding.progressBar


//        binding.btnShovORhide.setOnClickListener {
//            val thisButton = it as ImageButton
//            UIHelper.hideShowPassword(evPassword,thisButton)
//        }

        signUpViewModel.uiState.onEach { state ->
            progressBar.isVisible = state.isLoading

            binding.apply {
                signUpEmail.error = state.error.takeIf { it.isNotEmpty() }
                signUpUsername.error = state.error.takeIf { it.isNotEmpty() }
            }

            state.user?.let {
                Toast.makeText(requireContext(),"You have registered. Go to login!",Toast.LENGTH_LONG)
                findNavController().navigate(R.id.signInFragment)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.signUpButton.setOnClickListener {
            val username = binding.signUpUsername.text.toString()
            val email = binding.signUpEmail.text.toString()
            val password  = binding.signUpPassword.text.toString()

            signUpViewModel.registerWithEmail(username,email, password)
        }

        return  binding.root
    }
}