package com.example.vegasapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vegasapp.databinding.ItemTicketBinding
import com.example.vegasapp.model.TicketResponse
import java.text.SimpleDateFormat
import java.util.Locale

class TicketsAdapter : RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>() {

    private var ticketsList = emptyList<TicketResponse>()

    fun setData(newData: List<TicketResponse>) {
        ticketsList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketsAdapter.TicketsViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketsAdapter.TicketsViewHolder, position: Int) {
        val currentItem = ticketsList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return ticketsList.size
    }

    inner class TicketsViewHolder(private val binding: ItemTicketBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ticketResponse: TicketResponse) {
            binding.apply {
                tvPrice.text = "Stake: "+ticketResponse.price.toString()
                tvWinAmount.text = "Payout: "+ticketResponse.winAmount.toString()

                // Format the date
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(ticketResponse.dateCreated)
                tvDateCreated.text = formattedDate

                tvFinished.text = if (ticketResponse.finished) "FINISHED" else "IN PLAY"

                if(ticketResponse.finished){
                    tvWon.text = if (ticketResponse.won == true) "WON" else "LOST"
                }else{
                    tvWon.text = ""
                }
            }
        }
    }


}