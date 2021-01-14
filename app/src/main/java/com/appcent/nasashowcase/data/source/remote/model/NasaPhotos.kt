package com.appcent.nasashowcase.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class NasaPhotos(@SerializedName("photos") var photos: List<PhotoModel>)

data class PhotoModel(
    @SerializedName("id") var photoId: Int,
    @SerializedName("sol") var solOfMars: Int,
    @SerializedName("camera") var roverCamera: RoverCamera,
    @SerializedName("img_src") var imgSource: String,
    @SerializedName("earth_date") var solToEarthDate: String,
    @SerializedName("rover") var rover: Rover
)

data class RoverCamera(
    @SerializedName("id") var cameraId: Int,
    @SerializedName("name") var cameraName: String,
    @SerializedName("rover_id") var roverId: Int,
    @SerializedName("full_name") var fullCameraName: String
)

data class Rover(
    @SerializedName("id") var roverId: Int,
    @SerializedName("name") var roverName: String,
    @SerializedName("landing_date") var landingDate: String,
    @SerializedName("launch_date") var launchDate: String,
    @SerializedName("status") var status: String
)
