package com.deepak.playoassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deepak.playoassignment.data.model.Article
import com.deepak.playoassignment.databinding.AdapterNewsBinding

class NewsAdapter(private val articles: List<Article>?) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.mAuthor.text = articles?.get(position)?.author
        holder.mDescription.text = articles?.get(position)?.description
        holder.mTitle.text = articles?.get(position)?.title
        Glide.with(holder.itemView.context)
            .load(articles?.get(position)?.urlToImage)
            .into(holder.mImage)
    }

    inner class NewsViewHolder(binding: AdapterNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mAuthor: TextView = binding.author
        val mDescription: TextView = binding.description
        val mTitle: TextView = binding.title
        val mImage: ImageView = binding.image

    }

    override fun getItemCount(): Int {
        return articles?.size!!
    }
}