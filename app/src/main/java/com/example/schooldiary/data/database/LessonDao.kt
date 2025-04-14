package com.example.schooldiary.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons WHERE dayOfWeek = :dayOfWeek")
    suspend fun getLessonsByDay(dayOfWeek: Int): List<Lesson>

    @Query("SELECT * FROM lessons WHERE subjectId = :subjectId")
    suspend fun getLessonsBySubject(subjectId: Long): List<Lesson> // Changed from Int to Long

    @Insert
    suspend fun insert(lesson: Lesson)
}