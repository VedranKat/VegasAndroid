package com.example.vegasapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vegasapp.databinding.ItemTicketgameBinding
import com.example.vegasapp.model.TicketGameResponse
import java.text.DecimalFormat

class TicketGamesAdapter: RecyclerView.Adapter<TicketGamesAdapter.TicketGamesViewHolder>() {

    private var ticketGamesList = emptyList<TicketGameResponse>()

    fun setData(newData: List<TicketGameResponse>) {
        ticketGamesList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketGamesAdapter.TicketGamesViewHolder {
        val binding = ItemTicketgameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketGamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketGamesAdapter.TicketGamesViewHolder, position: Int) {
        val currentItem = ticketGamesList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return ticketGamesList.size
    }

    inner class TicketGamesViewHolder(private val binding: ItemTicketgameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ticketGameResponse: TicketGameResponse) {
            binding.apply {
                tvChosenWinner.text = "Choice: "+ticketGameResponse.chosenWinner

                if (ticketGameResponse.gameResponse.finished){

                    val decimalFormat = DecimalFormat("0") // Format without decimal places

                    val homeTeamScoreFormatted = decimalFormat.format(ticketGameResponse.gameResponse.homeTeamScore)
                    val awayTeamScoreFormatted = decimalFormat.format(ticketGameResponse.gameResponse.awayTeamScore)

                    tvHomeTeamScore.text = "Score: $homeTeamScoreFormatted"
                    tvAwayTeamScore.text = "Score: $awayTeamScoreFormatted"

                    if (ticketGameResponse.gameResponse.winner.equals(ticketGameResponse.chosenWinner)) {
                        tvWinStatus.text = "Won"
                    }else
                        tvWinStatus.text = "Lost"
                }else{
                    tvWinStatus.text = ""
                    tvHomeTeamScore.text = "Score: "
                    tvAwayTeamScore.text = "Score: "
                }

                tvHomeTeamName.text = ticketGameResponse.gameResponse.homeTeam
                tvHomeTeamOdds.text = "Odds: "+ticketGameResponse.gameResponse.homeTeamOdd.toString()
                tvAwayTeamName.text = ticketGameResponse.gameResponse.awayTeam
                tvAwayTeamOdds.text = "Odds: "+ticketGameResponse.gameResponse.awayTeamOdd.toString()
            }
        }
    }
}