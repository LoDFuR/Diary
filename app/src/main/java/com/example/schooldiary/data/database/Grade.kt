package com.example.schooldiary.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grades")
data class Grade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lessonId: Int,
    val score: Int,
)