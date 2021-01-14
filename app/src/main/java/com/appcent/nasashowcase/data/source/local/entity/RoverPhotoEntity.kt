package com.appcent.nasashowcase.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.appcent.nasashowcase.data.source.local.RoomConverters

@Entity(tableName = "RoverPhoto")
@TypeConverters(RoomConverters::class)
data class RoverPhotoEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "photoid") var photoId: Int,
    @ColumnInfo(name = "solofmars") var solOfMars: Int,
    @ColumnInfo(name = "rovercamera") var roverCamera: RoverCameraEntity,
    @ColumnInfo(name = "imgsource") var imgSource: String,
    @ColumnInfo(name = "soltoearthdate") var solToEarthDate: String,
    @ColumnInfo(name = "rover") var rover: RoverEntity
)