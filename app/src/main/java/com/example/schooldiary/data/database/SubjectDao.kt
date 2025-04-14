package com.example.schooldiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects")
    suspend fun getAll(): List<Subject>

    @Query("SELECT * FROM subjects WHERE quarter = :quarter")
    suspend fun getSubjectsByQuarter(quarter: Int): List<Subject>

    @Insert
    suspend fun insert(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)
}