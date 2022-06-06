package com.example.task1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.task1.newslist.Article


abstract class BaseAdapter<T>:RecyclerView.Adapter<BaseAdapter.GenericViewHolder<T>>() {
   abstract class GenericViewHolder<T>( itemView :View):RecyclerView.ViewHolder(itemView),BindViewHolder<T>{
       abstract override fun onBind(item:T)
   }

    abstract fun setItem(items:MutableList<T>)

    abstract fun setViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        return setViewHolder(parent,viewType)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
       // val article=difference.currentList[position]
        holder.onBind(getItemViewType(position))
    }

    override fun getItemCount(): Int {
        return difference.currentList.size
    }
}