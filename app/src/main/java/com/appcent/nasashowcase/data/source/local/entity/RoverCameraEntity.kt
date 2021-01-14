package com.appcent.nasashowcase.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.appcent.nasashowcase.data.source.local.RoomConverters

@Entity(tableName = "RoverCamera")
@TypeConverters(RoomConverters::class)
data class RoverCameraEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "cameraid") var cameraId: Int,
    @ColumnInfo(name = "cameraname") var cameraName: String,
    @ColumnInfo(name = "roverid") var roverId: Int,
    @ColumnInfo(name = "fullname") var fullCameraName: String
)