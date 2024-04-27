package com.dicoding.asclepius.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnalyzeHistory (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var predictedLabel : String,
    var confidenceScore : String,
    var imageUri : String
)