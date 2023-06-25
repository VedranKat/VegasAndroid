package com.example.vegasapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vegasapp.databinding.ItemArticlePreviewBinding
import com.example.vegasapp.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ArticleViewHolder {
        val binding = ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply{
            Glide.with(this).load(article.urlToImage).into(holder.binding.ivArticleImage)
            holder.binding.tvSource.text = article.source?.name
            holder.binding.tvTitle.text = article.title
            holder.binding.tvDescription.text = article.description
            holder.binding.tvPublishedAt.text = article.publishedAt
            setOnClickListener{
                onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)?= null

    fun setOnItemClickListener(listener: (Article)-> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}