package com.example.ecommercetask.presentation.sign_in

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommercetask.R
import com.example.ecommercetask.databinding.FragmentSignInBinding
import com.example.ecommercetask.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignInFragment: BaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {

    val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)



        signInViewModel.uiState.onEach { state ->
            binding.progressBar.isVisible =
                state.isLoading
            binding.etEmail.error =
                state.error.takeIf { it.isNotEmpty() } // Set error only if not empty
            binding.etPassword.error =
                state.error.takeIf { it.isNotEmpty() } // Set error only if not empty
            if (state.user != null) {
                Log.d("SUCCESSFUL_SIGN_IN", "successfull")
                val user = state.user
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            Log.d("REGISTER_IS_COMPLETED","${email},${password}")
            signInViewModel.signInWithEmailAndPassword(email, password)
        }

        return binding.root
    }
}
