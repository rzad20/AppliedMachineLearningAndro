package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.AnalyzeHistory

@Database(entities = [AnalyzeHistory::class], version = 1, exportSchema = false)
abstract class AnalyzeHistoryDatabase : RoomDatabase() {
    abstract fun AnalyzeDao() : AnalyzeDao
    companion object {
        @Volatile
        private var instance: AnalyzeHistoryDatabase? = null
        fun getInstance(context: Context) : AnalyzeHistoryDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AnalyzeHistoryDatabase::class.java, "History.db"
                ).build()
            }

    }
}