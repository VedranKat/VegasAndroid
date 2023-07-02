package com.example.vegasapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.HttpException
import com.example.vegasapp.R
import com.example.vegasapp.api.VegasRetrofitInstance
import com.example.vegasapp.databinding.FragmentRegisterBinding
import com.example.vegasapp.model.authentication.RegisterRequest
import com.example.vegasapp.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.registerEmailEditText.text.toString()
            val password = binding.registerPasswordEditText.text.toString()
            val confirmPassword = binding.registerConfirmPasswordEditText.text.toString()

            if (password.equals(confirmPassword)) {
                // Perform register
                register(email, password, confirmPassword)
            } else {
                // Show error
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun register(email: String, password: String, confirmPassword: String) {

        val registerRequest = RegisterRequest(email, password, confirmPassword)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    // Call the login API function
                    VegasRetrofitInstance.api.register(registerRequest)
                }

                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    val token = tokenResponse?.token

                    if (token != null) {
                        TokenManager.saveToken(requireContext(), token)
                        navigateToNewsFragment()
                    }

                } else {
                    Log.e("RegisterFragment", response.errorBody().toString())
                }
            } catch (e: HttpException) {
                Log.e("RegisterFragment", e.toString())
            } catch (e: Exception) {
                Log.e("RegisterFragment", e.toString())
            }
        }
    }

    private fun navigateToNewsFragment() {
        val navController = findNavController()

        navController.navigate(R.id.newsFragment)
    }


}