package com.appcent.nasashowcase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appcent.nasashowcase.databinding.ListItemFilterBinding

class FilterAdapter(): ListAdapter<String, FilterAdapter.FilterItemHolder>(FilterDiffCallback()) {

    internal var onFilterSelected: (filter: String) -> Unit =
        { _ -> }

    override fun onBindViewHolder(holder: FilterItemHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, onFilterSelected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemHolder {
        return FilterItemHolder.from(parent)
    }

    class FilterItemHolder private constructor(val binding: ListItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, onFilterSelected: (String) -> Unit) {
            binding.cameraName = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onFilterSelected(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): FilterItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFilterBinding.inflate(layoutInflater, parent, false)
                return FilterItemHolder(binding)
            }
        }
    }
}

class FilterDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}