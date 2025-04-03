package com.example.schooldiary.data.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.schooldiary.data.models.Subject
import androidx.room.*
//import com.example.schedule.data.models.Subject

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<Subject>

    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getSubjectById(id: Int): Subject?
}