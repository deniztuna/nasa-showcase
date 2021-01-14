package com.appcent.nasashowcase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appcent.nasashowcase.R
import com.appcent.nasashowcase.data.source.remote.model.PhotoModel
import com.appcent.nasashowcase.databinding.ListItemNasaphotoBinding
import com.appcent.nasashowcase.ui.curiosity.CuriosityViewModel
import timber.log.Timber

class RoverPhotoAdapter: ListAdapter<PhotoModel, RecyclerView.ViewHolder>(NoteDiffCallback()) {

    private var showLoadingMore = false
    private val loadingMoreItemPosition: Int
        get() = if (showLoadingMore) itemCount - 1 else RecyclerView.NO_POSITION

    fun dataStartedLoading() {
        if (showLoadingMore) return
        showLoadingMore = true
        notifyItemInserted(loadingMoreItemPosition)
    }

    fun dataFinishedLoading() {
        if (!showLoadingMore) return
        val loadingPos = loadingMoreItemPosition
        showLoadingMore = false
        notifyItemRemoved(loadingPos)
    }

    internal var onRoverPhotoSelected: (photoModel: PhotoModel, root: View) -> Unit =
        { _, _ -> }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ROVER_PHOTO -> {
                val itemViewHolder = holder as RoverPhotoItemHolder
                itemViewHolder.bind(getItem(position), onRoverPhotoSelected)
            }
            TYPE_LOADING_MORE -> {
                val itemViewHolder = holder as LoadingMoreHolder
                bindLoadingViewHolder(itemViewHolder, position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < currentList.size && currentList.isNotEmpty()) {
            return TYPE_ROVER_PHOTO
        }
        return TYPE_LOADING_MORE
    }

    override fun getItemCount() = currentList.size + if (showLoadingMore) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING_MORE -> LoadingMoreHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_loadmore, parent, false)
            )
            TYPE_ROVER_PHOTO -> RoverPhotoItemHolder.from(parent)
            else -> throw IllegalStateException("Unsupported View type")
        }
    }

    private class LoadingMoreHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val progress = itemView as ProgressBar

        fun setVisibility(visibility: Int) {
            progress.visibility = visibility
        }
    }

    private fun bindLoadingViewHolder(holder: LoadingMoreHolder, position: Int) {
        holder.setVisibility(if (position > 0 && showLoadingMore && currentList.size > 0) View.VISIBLE else View.INVISIBLE)
    }

    class RoverPhotoItemHolder private constructor(val binding: ListItemNasaphotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoModel, onRoverPhotoSelected: (PhotoModel, View) -> Unit) {
            binding.photoUrl = item.imgSource
            ViewCompat.setTransitionName(binding.root, item.imgSource)
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onRoverPhotoSelected(item, binding.root)
            }
        }

        companion object {
            fun from(parent: ViewGroup): RoverPhotoItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNasaphotoBinding.inflate(layoutInflater, parent, false)
                return RoverPhotoItemHolder(binding)
            }
        }
    }

    companion object {
        private const val TYPE_LOADING_MORE = -1
        private const val TYPE_ROVER_PHOTO = 0
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<PhotoModel>() {
    override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem == newItem
    }
}