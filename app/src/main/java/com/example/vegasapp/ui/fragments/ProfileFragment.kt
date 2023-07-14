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
import com.example.vegasapp.db.GameDatabase
import com.example.vegasapp.model.Game
import com.example.vegasapp.model.PlayGameRequest
import com.example.vegasapp.model.PlayTicketRequest
import com.example.vegasapp.repository.GameRepositoryDB
import kotlinx.coroutines.launch
import java.math.BigDecimal
import com.example.vegasapp.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        setUpOnClickListeners()
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

    private fun setUpPlayTicketButton() {
        //No DI
        val gameDb = GameDatabase.invoke(requireContext())
        val gameRepositoryDB = GameRepositoryDB(gameDb)

        binding.btnPlayTicket.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {

                var multiplier = 1.0

                val games = gameRepositoryDB.getAllGames()

                val playGameRequests: MutableList<PlayGameRequest> = mutableListOf()

                games.stream().forEach {
                    //Multiply all odds
                    if(it.chosenWinner.equals(it.homeTeam)){
                        multiplier *= it.homeTeamOdd!!
                    } else {
                        multiplier *= it.awayTeamOdd!!
                    }

                    val playGameRequest = PlayGameRequest(
                        it.gameApiId!!,
                        it.chosenWinner
                    )

                    playGameRequests.add(playGameRequest)
                }

                val price = binding.etFunds.text.toString().toDoubleOrNull() ?: 0.0

                val playTicketRequest = PlayTicketRequest(
                    email!!,
                    BigDecimal.valueOf(price),
                    BigDecimal.valueOf(price*multiplier),
                    playGameRequests
                )

                if(games.isNotEmpty() && balance!! >= BigDecimal.valueOf(price)){
                    Toast.makeText(requireContext(), "Ticket created", Toast.LENGTH_SHORT).show()

                    val response = VegasRetrofitInstance.api.createTicket(
                        token!!, playTicketRequest
                    )

                    //Fetch balance again
                    setupProfile()

                    //Delete all games from DB and clear funds
                    gameRepositoryDB.deleteAllGames()
                    binding.etFunds.text.clear()

                } else {
                    Toast.makeText(requireContext(), "Ticket not created", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun setUpOnClickListeners(){
        setupLogoutButton()
        setUpViewTicketsButton()
        setUpPlayTicketButton()
    }

    private fun setupLogoutButton(){
        binding.btnLogout.setOnClickListener {
            TokenManager.saveToken(requireContext(), "")
            requireActivity().finish()
        }
    }

    private fun setUpViewTicketsButton() {
        binding.btnViewTicket.setOnClickListener {
            navigateToViewTicketsFragment()
        }
    }

    private fun navigateToViewTicketsFragment() {
        val navController = findNavController()

        navController.navigate(R.id.activeTicketGamesFragment)
    }

}