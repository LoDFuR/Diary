package com.example.schooldiary.data.database

import androidx.room.*
import com.example.schooldiary.data.models.Lesson

@Dao
interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lesson: Lesson)

    @Update
    suspend fun update(lesson: Lesson)

    @Delete
    suspend fun delete(lesson: Lesson)

    @Query("SELECT * FROM lessons")
    suspend fun getAllLessons(): List<Lesson>

    @Query("SELECT * FROM lessons WHERE subjectId = :subjectId")
    suspend fun getLessonsBySubject(subjectId: Int): List<Lesson>
}