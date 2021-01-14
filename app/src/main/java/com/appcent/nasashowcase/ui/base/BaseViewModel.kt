package com.appcent.nasashowcase.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcent.nasashowcase.data.source.ImplNasaRepository
import com.appcent.nasashowcase.data.source.remote.model.NasaPhotos
import com.appcent.nasashowcase.data.source.remote.model.PhotoModel
import com.appcent.nasashowcase.manager.NasaRemoteConfigManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
open class BaseViewModel constructor(private val implNasaRepository: ImplNasaRepository) : ViewModel() {

    var page = 1
    val _nasaPhotos = MutableLiveData<List<PhotoModel>>()

    val isDataSetEmpty = Transformations.map(_nasaPhotos) {
        it.isEmpty()
    }

    var cameraName: String? = null
    val loadingState = MutableLiveData<Boolean>().apply { postValue(false) }
    var roverName: String? = null
    var solDay: Long = NasaRemoteConfigManager.getInstance().solDay
    var apiKey: String = NasaRemoteConfigManager.getInstance().apiKey

    @ExperimentalCoroutinesApi
    private fun loadRoverPhotosWithoutFilter() {
        viewModelScope.launch {
            if (page != 1) loadingState.postValue(true)
            implNasaRepository.getPhotosOfRoverWithPagination(roverName ?: "", solDay.toInt(), apiKey, page).onStart { Log.e("DT:", "flow started, show loading state") }
                .catch { exception -> Timber.e("DT:exception occured in flow, show error state ${exception.message}") }
                .collect { nasaPhotos ->
                    collectRoverPhotos(nasaPhotos)
                }
        }
    }

    @ExperimentalCoroutinesApi
    private fun loadRoverPhotosOfCamera(cameraName: String) {
        viewModelScope.launch {
            if (page != 1) loadingState.postValue(true)
            implNasaRepository.getPhotosOfRoverWithSpecificCamera(roverName ?: "", solDay.toInt(), apiKey, page, cameraName).onStart { Log.e("DT:", "flow started, show loading state") }
                .catch { exception -> Timber.e("DT:exception occured in flow, show error state ${exception.message}") }
                .collect { nasaPhotos ->
                    collectRoverPhotos(nasaPhotos)
                }
        }
    }

    fun loadRoverPhotos() {
        cameraName?.let {
            loadRoverPhotosOfCamera(it)
        } ?: run {
            loadRoverPhotosWithoutFilter()
        }
    }

    fun resetPagination() {
        page = 1
        clearData()
    }

    private fun clearData() {
        _nasaPhotos.value = emptyList()
    }

    private fun collectRoverPhotos(nasaPhotos: NasaPhotos) {
        if (_nasaPhotos.value.isNullOrEmpty()) {
            _nasaPhotos.value = nasaPhotos.photos
        } else {
            var oldData = _nasaPhotos.value!!.toMutableList()
            oldData.addAll(nasaPhotos.photos)
            _nasaPhotos.value = oldData
        }
        page++
        loadingState.postValue(false)
        Timber.e("DT:: size of list --> ${nasaPhotos.photos.size}")
    }
}