package com.example.vegasapp.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    lateinit var binding: FragmentGamesBinding

    val TAG = "GamesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentGamesBinding.bind(view)
        setupRecyclerView()

        viewModel.games.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { gamesResponse ->
                        gamesAdapter.setData(gamesResponse) // Submit the whole list directly
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