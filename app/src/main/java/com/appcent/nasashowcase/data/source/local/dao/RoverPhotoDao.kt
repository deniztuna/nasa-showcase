package com.appcent.nasashowcase.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.appcent.nasashowcase.data.source.local.entity.RoverPhotoEntity

@Dao
interface RoverPhotoDao : BaseDao<RoverPhotoEntity> {
    @Query("SELECT * FROM RoverPhoto")
    fun observeRoverPhotoEntities(): LiveData<List<RoverPhotoEntity>>

    @Query("SELECT * FROM RoverPhoto WHERE photoid = :photoId")
    fun observeRoverPhotoEntity(photoId: Int): LiveData<RoverPhotoEntity>

    @Query("SELECT * FROM RoverPhoto")
    suspend fun getRoverPhotoEntities(): List<RoverPhotoEntity>

    @Query("SELECT * FROM RoverPhoto WHERE photoid = :photoId")
    suspend fun getRoverPhotoEntityWithId(photoId: Int): RoverPhotoEntity?

    @Query("DELETE FROM RoverPhoto WHERE photoid = :photoId")
    suspend fun deleteRoverPhotoEntityWithId(photoId: Int): Int

    @Query("DELETE FROM RoverPhoto")
    suspend fun deleteTasks()
}