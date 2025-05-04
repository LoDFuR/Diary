package com.example.schooldiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LessonDao {
    @Insert
    suspend fun insert(lesson: Lesson)

    @Update
    suspend fun update(lesson: Lesson)

    @Delete
    suspend fun delete(lesson: Lesson)

    @Query("SELECT * FROM lessons WHERE dayOfWeek = :dayOfWeek")
    suspend fun getLessonsByDay(dayOfWeek: Int): List<Lesson>

    @Query("SELECT * FROM lessons WHERE subjectId = :subjectId")
    suspend fun getLessonsBySubject(subjectId: Long): List<Lesson>
}