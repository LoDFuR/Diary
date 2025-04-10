package com.example.schooldiary.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectId: Int,
    val dayOfWeek: Int, // 0 = Пн, 1 = Вт, ..., 6 = Вс
)