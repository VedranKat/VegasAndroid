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
import com.example.vegasapp.R
import com.example.vegasapp.api.VegasRetrofitInstance
import com.example.vegasapp.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import java.math.BigDecimal
import com.example.vegasapp.util.TokenManager

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding
    val TAG = "ProfileFragment"

    var token: String? = null
    var email: String? = null
    var balance: BigDecimal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfile()
        setupLogoutButton()
        setUpViewTicketsButton()
    }

    // Refresh profile when returning to fragment
    override fun onResume() {
        super.onResume()
        setupProfile()
    }

    private fun setupProfile() {

        token = TokenManager.getToken(requireContext())
        email = TokenManager.getSub(requireContext())

        binding.tvEmail.text = email

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = VegasRetrofitInstance.api.getUser(
                    token!!, email!!
                )

                if (response.isSuccessful) {
                    val profile = response.body()
                    if (profile != null) {
                        binding.tvBalance.text = profile.balance.toString()
                        balance = profile.balance
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "setupProfile: ${e.message}")
            }
        }
    }

    fun setupLogoutButton(){
        binding.btnLogout.setOnClickListener {
            TokenManager.saveToken(requireContext(), "")
            requireActivity().finish()
        }
    }

    fun setUpViewTicketsButton() {
        binding.btnViewTicket.setOnClickListener {
            navigateToViewTicketsFragment()
        }
    }

    private fun navigateToViewTicketsFragment() {
        val navController = findNavController()

        navController.navigate(R.id.activeTicketGamesFragment)
    }

}