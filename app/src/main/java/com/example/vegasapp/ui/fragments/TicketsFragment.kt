package com.example.vegasapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vegasapp.R
import com.example.vegasapp.adapters.TicketsAdapter
import com.example.vegasapp.databinding.FragmentTicketsBinding
import com.example.vegasapp.ui.TicketsViewModel
import com.example.vegasapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val viewModel: TicketsViewModel by viewModels()
    lateinit var ticketsAdapter: TicketsAdapter

    lateinit var binding: FragmentTicketsBinding

    val TAG = "TicketsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTicketsBinding.bind(view)
        setupRecyclerView()

        viewModel.tickets.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { ticketsResponse ->
                        ticketsAdapter.setData(ticketsResponse) // Submit the whole list directly
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
        ticketsAdapter = TicketsAdapter()
        binding.rvTickets.apply {
            adapter = ticketsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
}