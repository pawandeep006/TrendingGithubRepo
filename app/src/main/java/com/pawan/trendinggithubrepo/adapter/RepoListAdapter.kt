package com.pawan.trendinggithubrepo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pawan.trendinggithubrepo.databinding.RepoListItemBinding
import com.pawan.trendinggithubrepo.source.network.model.Item
import java.util.*

class RepoListAdapter(private val onClickListener: (Any) -> Unit) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    var items: MutableList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RepoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            bind(View.OnClickListener {
                onClickListener(item)
            }, item)
        }
    }

    fun setData(it: List<Item>) {
        items.addAll(it)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, data: Item) {
            binding.apply {
                onClick = listener
                item = data
                executePendingBindings()
            }
        }
    }
}