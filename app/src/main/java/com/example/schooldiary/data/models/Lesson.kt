package com.example.schooldiary.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectId: Int,
    val date: String,
    val assignments: String, // Можно использовать JSON для хранения
    val grades: String // Можно использовать JSON для хранения
)