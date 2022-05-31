package com.example.task1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.databinding.ItemNewsBinding
import com.example.task1.newslist.Article
import com.example.task1.ui.fragments.NewsFragment

class NewsAdapter(private val context: NewsFragment):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder( val binding:ItemNewsBinding):RecyclerView.ViewHolder(binding.root)

    private val differenceCallBack=object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url  ==newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }
     val difference=AsyncListDiffer(this,differenceCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article=difference.currentList[position]
        holder.binding.apply {
            Glide
                .with(context)
                .load(article.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(imageIV)
            sourceTV.text=article.source?.name
            titleTV.text=article.title
            descriptionTV.text=article.description
            publishTimeTV.text=article.publishedAt
        }

    }

    override fun getItemCount(): Int {
        return difference.currentList.size
    }
}