package com.example.vegasapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.HttpException
import com.example.vegasapp.R
import com.example.vegasapp.api.VegasRetrofitInstance
import com.example.vegasapp.databinding.FragmentLoginBinding
import com.example.vegasapp.db.GameDatabase
import com.example.vegasapp.model.authentication.LoginRequest
import com.example.vegasapp.repository.GameRepositoryDB
import com.example.vegasapp.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            cleanUpOldGames()

            // Perform login
            login(email, password)
        }

        binding.registerButton.setOnClickListener {
            navigateToRegisterFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login(email: String, password: String) {

        val loginRequest = LoginRequest(email, password)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    // Call the login API function
                    VegasRetrofitInstance.api.login(loginRequest)
                }

                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    val token = tokenResponse?.token

                    if (token != null) {
                        TokenManager.saveToken(requireContext(), token)
                        navigateToNewsFragment()
                        }

                } else {
                    Log.e("LoginFragment", response.errorBody().toString())
                }
            } catch (e: HttpException) {
                Log.e("LoginFragment", e.toString())
            } catch (e: Exception) {
                Log.e("LoginFragment", e.toString())
            }
        }
    }

    private fun cleanUpOldGames() {

        //No DI
        val gameDb = GameDatabase.invoke(requireContext())
        val gameRepositoryDB = GameRepositoryDB(gameDb)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    gameRepositoryDB.deleteAllGames()
                }
            } catch (e: Exception) {
                Log.e("LoginFragment", e.toString())
            }
        }
    }

    private fun navigateToRegisterFragment() {
        val navController = findNavController()

        navController.navigate(R.id.registerFragment)
    }

    private fun navigateToNewsFragment() {
        val navController = findNavController()

        navController.navigate(R.id.newsFragment)
    }
}