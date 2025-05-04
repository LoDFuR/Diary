package com.example.schooldiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GradeDao {
    @Query("SELECT * FROM grades WHERE subjectId = :subjectId")
    suspend fun getGradesForSubject(subjectId: Long): List<Grade>

    @Insert
    suspend fun insertGrade(grade: Grade)

    @Delete
    suspend fun deleteGrade(grade: Grade)

    @Update
    suspend fun updateGrade(grade: Grade)
}