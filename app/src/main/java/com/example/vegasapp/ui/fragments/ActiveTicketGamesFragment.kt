package com.example.vegasapp.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vegasapp.R
import com.example.vegasapp.adapters.ActiveTicketGamesAdapter
import com.example.vegasapp.databinding.FragmentActiveticketgamesBinding
import com.example.vegasapp.model.Game
import com.example.vegasapp.ui.ActiveTicketGamesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActiveTicketGamesFragment: Fragment(R.layout.fragment_activeticketgames) {

    private val viewModel: ActiveTicketGamesViewModel by viewModels()
    lateinit var activeTicketGamesAdapter: ActiveTicketGamesAdapter

    lateinit var binding: FragmentActiveticketgamesBinding

    val TAG = "ActiveTicketGamesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentActiveticketgamesBinding.bind(view)
        setupRecyclerView()

        viewModel.games.observe(viewLifecycleOwner) { response ->
            response?.let { gamesResponse ->
                activeTicketGamesAdapter.setData(gamesResponse) // Submit the whole list directly
            }
        }

        activeTicketGamesAdapter.setOnItemClickListener { game ->
            showAlertDialog(game)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshGames()
    }

    private fun setupRecyclerView() {
        activeTicketGamesAdapter = ActiveTicketGamesAdapter()
        binding.rvActiveTicketGames.apply{
            adapter = activeTicketGamesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showAlertDialog(game: Game) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Remove this game?:")

        builder.setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
            viewModel.deleteGame(game)
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->

            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }



}