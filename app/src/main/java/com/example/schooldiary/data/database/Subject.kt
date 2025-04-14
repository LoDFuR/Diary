package com.example.schooldiary.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // Changed from Int to Long
    val name: String,
    val quarter: Int
)