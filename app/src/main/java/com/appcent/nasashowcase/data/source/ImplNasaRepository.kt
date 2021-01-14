package com.appcent.nasashowcase.data.source

import com.appcent.nasashowcase.data.source.remote.NasaService
import com.appcent.nasashowcase.data.source.remote.model.NasaPhotos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplNasaRepository @Inject constructor(private val nasaService: NasaService) : NasaRepository {
    @ExperimentalCoroutinesApi
    override fun getPhotosOfRoverWithoutPagination(
        roverName: String,
        sol: Int,
        apiKey: String
    ): Flow<NasaPhotos> {
        return flow {
            val photoList = nasaService.getPhotosOfRoverWithoutPagination(roverName, sol, apiKey)
            emit(photoList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    @ExperimentalCoroutinesApi
    override fun getPhotosOfRoverWithPagination(
        roverName: String,
        sol: Int,
        apiKey: String,
        page: Int
    ): Flow<NasaPhotos> {
        return flow {
            val photoList = nasaService.getPhotosOfRoverWithPagination(roverName, sol, apiKey, page)
            emit(photoList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    @ExperimentalCoroutinesApi
    override fun getPhotosOfRoverWithSpecificCamera(
        roverName: String,
        sol: Int,
        apiKey: String,
        page: Int,
        camera: String
    ): Flow<NasaPhotos> {
        return flow {
            val photoList = nasaService.getPhotosOfRoverWithSpecificCamera(roverName, sol, apiKey, page, camera)
            emit(photoList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    @ExperimentalCoroutinesApi
    override fun getPhotosOfRoverWithSpecificCameraWithoutPagination(
        roverName: String,
        sol: Int,
        apiKey: String,
        camera: String
    ): Flow<NasaPhotos> {
        return flow {
            val photoList = nasaService.getPhotosOfRoverWithSpecificCameraWithoutPagination(roverName, sol, apiKey, camera)
            emit(photoList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }
}