package com.appcent.nasashowcase.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appcent.nasashowcase.R
import com.appcent.nasashowcase.data.source.remote.model.PhotoModel
import com.appcent.nasashowcase.ui.adapter.FilterAdapter
import com.appcent.nasashowcase.ui.adapter.RoverPhotoAdapter
import com.appcent.nasashowcase.util.GlideApp
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, notes: List<PhotoModel>?) {
    notes?.let {
        (recyclerView.adapter as RoverPhotoAdapter).submitList(it)
    }
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(6))
    GlideApp.with(view.context)
        .load(url ?: "")
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .dontAnimate()
        .apply(requestOptions)
        .into(view)
}

@BindingAdapter("app:loadfilters")
fun loadFilters(recyclerView: RecyclerView, notes: List<String>?) {
    notes?.let {
        (recyclerView.adapter as FilterAdapter).submitList(it)
    }
}