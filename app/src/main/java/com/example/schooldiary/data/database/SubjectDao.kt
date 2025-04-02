package com.example.schooldiary.data.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.schooldiary.data.models.Subject

//import com.example.schedule.data.models.Subject

@Dao
interface SubjectDao {
    @Insert
    suspend fun insert(subject: Subject)

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<Subject>
}