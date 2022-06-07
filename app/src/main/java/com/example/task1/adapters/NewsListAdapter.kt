package com.example.task1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.databinding.ItemNewsBinding
import com.example.task1.newslist.Article
import com.example.task1.ui.fragments.NewsFragment

class NewsListAdapter(private val context: NewsFragment):BaseAdapter<Article>() {

    inner class NewsViewHolder (private val binding :ItemNewsBinding):GenericViewHolder<Article>(binding.root) {
        override fun onBind(item: Article) {
            binding.apply {
                Glide
                    .with(context)
                    .load(item.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .into(imageIV)
                sourceTV.text = item.source?.name
                titleTV.text = item.title
                descriptionTV.text = item.description
                publishTimeTV.text = item.publishedAt
            }
        }
    }

    private val differenceCallBack=object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.url==newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }
    val difference= AsyncListDiffer(this,differenceCallBack)

    override fun setViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<Article> {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun setItem(): MutableList<Article> {
       return difference.currentList
    }

}