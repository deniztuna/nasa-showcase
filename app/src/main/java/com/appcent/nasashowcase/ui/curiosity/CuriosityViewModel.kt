package com.appcent.nasashowcase.ui.curiosity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.appcent.nasashowcase.data.source.ImplNasaRepository
import com.appcent.nasashowcase.data.source.remote.model.PhotoModel
import com.appcent.nasashowcase.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CuriosityViewModel @ViewModelInject constructor(private val implNasaRepository: ImplNasaRepository) : BaseViewModel(implNasaRepository) {
    val nasaPhotos: LiveData<List<PhotoModel>>
        get() = _nasaPhotos

    init {
        roverName = "curiosity"
        loadRoverPhotos()
    }
}