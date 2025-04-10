package com.example.schooldiary.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
//import com.example.schooldiary.data;
@Dao
interface SchoolDao {
    @Insert
    suspend fun insertSubject(subject: Subject)

    @Insert
    suspend fun insertLesson(lesson: Lesson)

    @Insert
    suspend fun insertGrade(grade: Grade)

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<Subject>

    @Query("SELECT * FROM lessons WHERE dayOfWeek = :day")
    suspend fun getLessonsForDay(day: Int): List<Lesson>

    @Query("SELECT AVG(score) FROM grades WHERE lessonId = :lessonId")
    suspend fun getAverageScoreForLesson(lessonId: Int): Float?
}

