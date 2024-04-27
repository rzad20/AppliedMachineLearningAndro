package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.AnalyzeHistory

@Dao
interface AnalyzeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun insertHistory(users: AnalyzeHistory)

    @Query("SELECT * from AnalyzeHistory")
    fun getAllHistories(): LiveData<List<AnalyzeHistory>>


}