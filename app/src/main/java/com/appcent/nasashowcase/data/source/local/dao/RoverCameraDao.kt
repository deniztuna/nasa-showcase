package com.appcent.nasashowcase.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.appcent.nasashowcase.data.source.local.entity.RoverCameraEntity

@Dao
interface RoverCameraDao : BaseDao<RoverCameraEntity> {
    @Query("SELECT * FROM RoverCamera")
    fun observeTasks(): LiveData<List<RoverCameraEntity>>

    @Query("SELECT * FROM RoverCamera WHERE cameraid = :cameraId")
    fun observeTaskById(cameraId: Int): LiveData<RoverCameraEntity>

    @Query("SELECT * FROM RoverCamera")
    suspend fun getTasks(): List<RoverCameraEntity>

    @Query("SELECT * FROM RoverCamera WHERE cameraid = :cameraId")
    suspend fun getTaskById(cameraId: Int): RoverCameraEntity?

    @Query("DELETE FROM RoverCamera WHERE cameraid = :cameraId")
    suspend fun deleteRoverCameraEntityById(cameraId: Int): Int

    @Query("DELETE FROM RoverCamera")
    suspend fun deleteTasks()
}