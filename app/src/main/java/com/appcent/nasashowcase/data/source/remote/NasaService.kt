package com.appcent.nasashowcase.data.source.remote

import com.appcent.nasashowcase.data.source.remote.model.NasaPhotos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface NasaService {
    @GET("{roverName}/photos")
    suspend fun getPhotosOfRoverWithoutPagination(
        @Path("roverName") roverName: String,
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String): NasaPhotos

    @GET("{roverName}/photos")
    suspend fun getPhotosOfRoverWithPagination(
        @Path("roverName") roverName: String,
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int): NasaPhotos

    @GET("{roverName}/photos")
    suspend fun getPhotosOfRoverWithSpecificCamera(
        @Path("roverName") roverName: String,
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("camera") camera: String): NasaPhotos

    @GET("{roverName}/photos")
    suspend fun getPhotosOfRoverWithSpecificCameraWithoutPagination(
        @Path("roverName") roverName: String,
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String,
        @Query("camera") camera: String): NasaPhotos
}