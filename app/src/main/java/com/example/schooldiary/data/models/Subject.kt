package com.example.schooldiary.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val averageGrade: Double
)