package com.example.schooldiary.data.models
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.schooldiary.data.database.SubjectDao

//import com.example.schedule.data.database.SubjectDao

@Database(entities = [Subject::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
}