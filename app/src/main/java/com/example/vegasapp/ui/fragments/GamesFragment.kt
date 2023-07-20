package com.example.vegasapp.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vegasapp.R
import com.example.vegasapp.adapters.GamesAdapter
import com.example.vegasapp.databinding.FragmentGamesBinding
import com.example.vegasapp.model.GameResponse
import com.example.vegasapp.ui.GamesViewModel
import com.example.vegasapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : Fragment(R.layout.fragment_games){

    private val viewModel: GamesViewModel by viewModels()
    lateinit var gamesAdapter: GamesAdapter

    private var allGamesList = emptyList<GameResponse>()
    private var currentFilter = "All"

    lateinit var binding: FragmentGamesBinding

    val TAG = "GamesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentGamesBinding.bind(view)
        setUpFilterButtons()
        setupRecyclerView()

        viewModel.games.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { gamesResponse ->
                        allGamesList = gamesResponse
                        filterList(currentFilter)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }

                is Resource.Loading -> {
                    Log.e(TAG, "Loading...")
                }
            }
        }

        gamesAdapter.setOnItemClickListener { game ->
            showAlertDialog(game)
        }
    }

    private fun setupRecyclerView(){
        gamesAdapter = GamesAdapter()
        binding.rvGames.apply {
            adapter = gamesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setUpFilterButtons() {
        // Darken the "All" button by default
        darkenButton(binding.btnAll)

        binding.btnAll.setOnClickListener {
            currentFilter = "All"
            filterList(currentFilter)
            darkenButton(binding.btnAll)
            lightenButton(binding.btnMLB)
            lightenButton(binding.btnCFL)
        }

        binding.btnMLB.setOnClickListener {
            currentFilter = "MLB"
            filterList(currentFilter)
            darkenButton(binding.btnMLB)
            lightenButton(binding.btnAll)
            lightenButton(binding.btnCFL)
        }

        binding.btnCFL.setOnClickListener {
            currentFilter = "CFL"
            filterList(currentFilter)
            darkenButton(binding.btnCFL)
            lightenButton(binding.btnAll)
            lightenButton(binding.btnMLB)
        }
    }

    private fun darkenButton(button: Button) {
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_700))
    }

    private fun lightenButton(button: Button) {
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_700))
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
    }

    private fun filterList(filter: String) {
        val filteredList = when (filter) {
            "MLB" -> allGamesList.filter { game -> game.sportKey == "baseball_mlb" }
            "CFL" -> allGamesList.filter { game -> game.sportKey == "americanfootball_cfl" }
            else -> allGamesList
        }
        gamesAdapter.setData(filteredList)
    }

    private fun showAlertDialog(game: GameResponse) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Place your bet:")

        builder.setPositiveButton("${game.homeTeam}: ${game.homeTeamOdd}") { dialog: DialogInterface, _: Int ->
            viewModel.saveGameToDatabase(game, game.homeTeam)
            dialog.dismiss()
        }

        builder.setNegativeButton("${game.awayTeam}: ${game.awayTeamOdd}") { dialog: DialogInterface, _: Int ->
            viewModel.saveGameToDatabase(game, game.awayTeam)
            dialog.dismiss()
        }

        builder.setNeutralButton("Cancel") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}