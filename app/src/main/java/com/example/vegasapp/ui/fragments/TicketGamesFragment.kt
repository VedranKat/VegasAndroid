package com.example.vegasapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vegasapp.R
import com.example.vegasapp.adapters.TicketGamesAdapter
import com.example.vegasapp.adapters.TicketsAdapter
import com.example.vegasapp.databinding.FragmentTicketgamesBinding
import com.example.vegasapp.ui.TicketGamesViewModel
import com.example.vegasapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketGamesFragment: Fragment(R.layout.fragment_ticketgames) {

    private val viewModel: TicketGamesViewModel by viewModels()
    lateinit var ticketGamesAdapter: TicketGamesAdapter

    lateinit var binding: FragmentTicketgamesBinding

    val TAG = "TicketGamesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTicketgamesBinding.bind(view)
        setupRecyclerView()

        val ticketId = arguments?.getInt("ticketId") ?: 0

        viewModel.setTicketId(ticketId)

        viewModel.ticketGames.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { ticketsResponse ->
                        ticketGamesAdapter.setData(ticketsResponse) // Submit the whole list directly
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
    }

    private fun setupRecyclerView() {
        ticketGamesAdapter = TicketGamesAdapter()
        binding.rvTicketGames.apply {
            adapter = ticketGamesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}