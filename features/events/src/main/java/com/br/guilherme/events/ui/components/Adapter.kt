package com.br.guilherme.events.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.guilherme.events.databinding.ItemEventBinding
import com.br.guilherme.events.model.EventItem
import com.br.guilherme.events.ui.util.ItemClickListener
import com.br.guilherme.events.ui.util.RecyclerDiffUtil

class EventAdapter(private val mListener: ItemClickListener) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var items: List<EventItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEventBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventItem, listener : ItemClickListener) {
            binding.item = item
            binding.itemClick = listener
            binding.executePendingBindings()
        }
    }

    fun updateItems(newItems: List<EventItem>) {
        val diffResult = DiffUtil.calculateDiff(RecyclerDiffUtil(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], mListener)
    }
}