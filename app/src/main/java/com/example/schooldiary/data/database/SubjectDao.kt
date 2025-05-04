package com.example.schooldiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects WHERE quarter = :quarter")
    suspend fun getSubjectsByQuarter(quarter: Int): List<Subject>

    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getSubjectById(id: Long): Subject?

    @Insert
    suspend fun insert(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)

    @Query("SELECT * FROM subjects")
    suspend fun getAll(): List<Subject>
}