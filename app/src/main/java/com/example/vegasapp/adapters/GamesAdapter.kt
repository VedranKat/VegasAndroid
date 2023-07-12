package com.example.vegasapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vegasapp.databinding.ItemGameBinding
import com.example.vegasapp.model.Article
import com.example.vegasapp.model.GameResponse

class GamesAdapter : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    private var gamesList = emptyList<GameResponse>()

    fun setData(newData: List<GameResponse>) {
        gamesList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val currentItem = gamesList[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(currentItem)
            }
        }
    }

    override fun getItemCount() = gamesList.size

    inner class GamesViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gameResponse: GameResponse) {
            binding.apply {
                tvHomeTeam.text = gameResponse.homeTeam
                tvAwayTeam.text = gameResponse.awayTeam
                tvHomeTeamOdd.text = gameResponse.homeTeamOdd.toString()
                tvAwayTeamOdd.text = gameResponse.awayTeamOdd.toString()
            }
        }
    }

    private var onItemClickListener: ((GameResponse) -> Unit)?= null

    fun setOnItemClickListener(listener: (GameResponse)-> Unit){
        onItemClickListener = listener
    }
}
