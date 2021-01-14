package com.appcent.nasashowcase.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.appcent.nasashowcase.data.source.local.dao.RoverCameraDao
import com.appcent.nasashowcase.data.source.local.dao.RoverDao
import com.appcent.nasashowcase.data.source.local.dao.RoverPhotoDao
import com.appcent.nasashowcase.data.source.local.entity.RoverCameraEntity
import com.appcent.nasashowcase.data.source.local.entity.RoverEntity
import com.appcent.nasashowcase.data.source.local.entity.RoverPhotoEntity
import com.google.gson.Gson

@Database(entities = [RoverEntity::class, RoverCameraEntity::class, RoverPhotoEntity::class], version = 1, exportSchema = false)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun roverDao(): RoverDao
    abstract fun roverCameraDao(): RoverCameraDao
    abstract fun roverPhotoDao(): RoverPhotoDao
}

class RoomConverters {
    private val gson = Gson()
    @TypeConverter
    fun stringToRoverEntity(input: String?): RoverEntity? {
        return input?.let {
            gson.fromJson(it, RoverEntity::class.java)
        }
    }

    @TypeConverter
    fun roverEntityToString(input: RoverEntity?): String? {
        return input?.let {
            gson.toJson(input)
        }
    }

    @TypeConverter
    fun stringToRoverCameraEntity(input: String?): RoverCameraEntity? {
        return input?.let {
            gson.fromJson(it, RoverCameraEntity::class.java)
        }
    }

    @TypeConverter
    fun roverCameraEntityToString(input: RoverCameraEntity?): String? {
        return input?.let {
            gson.toJson(input)
        }
    }

}