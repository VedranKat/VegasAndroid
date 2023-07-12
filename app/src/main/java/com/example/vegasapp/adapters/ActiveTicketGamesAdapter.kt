package com.example.vegasapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vegasapp.databinding.ItemActiveticketgameBinding
import com.example.vegasapp.model.Game
import com.example.vegasapp.model.GameResponse

class ActiveTicketGamesAdapter : RecyclerView.Adapter<ActiveTicketGamesAdapter.ActiveTicketGamesViewHolder>() {

    private var activeTicketGamesList = emptyList<Game>()

    fun setData(newData: List<Game>) {
        activeTicketGamesList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActiveTicketGamesAdapter.ActiveTicketGamesViewHolder {
        val binding = ItemActiveticketgameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActiveTicketGamesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ActiveTicketGamesAdapter.ActiveTicketGamesViewHolder,
        position: Int
    ) {
        val currentItem = activeTicketGamesList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(currentItem)
            }
        }

    }

    override fun getItemCount(): Int {
        return activeTicketGamesList.size
    }

    inner class ActiveTicketGamesViewHolder(private val binding: ItemActiveticketgameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                tvChosenWinner.text = "Bet on: "+game.chosenWinner
                tvHomeTeamName.text = game.homeTeam
                tvAwayTeamName.text = game.awayTeam
                tvHomeTeamOdds.text = "Odds: "+game.homeTeamOdd.toString()
                tvAwayTeamOdds.text = "Odds: "+game.awayTeamOdd.toString()
            }
        }
    }

    private var onItemClickListener: ((Game) -> Unit)?= null

    fun setOnItemClickListener(listener: (Game)-> Unit){
        onItemClickListener = listener
    }


}