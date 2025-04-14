package com.example.schooldiary.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GradeDao {
    @Query("SELECT * FROM grades WHERE subjectId = :subjectId")
    suspend fun getGradesForSubject(subjectId: Long): List<Grade>

    @Insert
    suspend fun insertGrade(grade: Grade)
}