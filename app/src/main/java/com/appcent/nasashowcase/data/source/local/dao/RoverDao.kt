package com.appcent.nasashowcase.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.appcent.nasashowcase.data.source.local.entity.RoverEntity

@Dao
interface RoverDao : BaseDao<RoverEntity> {

    @Query("SELECT * FROM Rover")
    fun observeRoverEntities(): LiveData<List<RoverEntity>>

    @Query("SELECT * FROM Rover WHERE roverid = :roverId")
    fun observeRoverEntityWithId(roverId: Int): LiveData<RoverEntity>

    @Query("SELECT * FROM Rover")
    suspend fun getRoverEntities(): List<RoverEntity>

    @Query("SELECT * FROM Rover WHERE roverid = :roverId")
    suspend fun getRoverEntityWithId(roverId: String): RoverEntity?

    @Query("DELETE FROM Rover WHERE roverid = :roverId")
    suspend fun deleteRoverEntityWithId(roverId: Int): Int

    @Query("DELETE FROM Rover")
    suspend fun deleteTasks()
}