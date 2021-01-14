package com.appcent.nasashowcase.data.source

import com.appcent.nasashowcase.data.source.remote.model.NasaPhotos
import kotlinx.coroutines.flow.Flow

interface NasaRepository {

    fun getPhotosOfRoverWithoutPagination(roverName: String, sol: Int, apiKey: String): Flow<NasaPhotos>

    fun getPhotosOfRoverWithPagination(roverName: String, sol: Int, apiKey: String, page: Int): Flow<NasaPhotos>

    fun getPhotosOfRoverWithSpecificCamera(roverName: String, sol: Int, apiKey: String, page: Int, camera: String): Flow<NasaPhotos>

    fun getPhotosOfRoverWithSpecificCameraWithoutPagination(roverName: String, sol: Int, apiKey: String, camera: String): Flow<NasaPhotos>
}