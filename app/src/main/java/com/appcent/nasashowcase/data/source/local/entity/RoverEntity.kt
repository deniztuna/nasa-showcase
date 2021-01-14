package com.appcent.nasashowcase.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.appcent.nasashowcase.data.source.local.RoomConverters

@Entity(tableName = "Rover")
@TypeConverters(RoomConverters::class)
data class RoverEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "roverid") var roverId: Int,
    @ColumnInfo(name = "rovername") var roverName: String,
    @ColumnInfo(name = "landingdate") var landingDate: String,
    @ColumnInfo(name = "launchdate") var launchDate: String,
    @ColumnInfo(name = "status") var status: String
)