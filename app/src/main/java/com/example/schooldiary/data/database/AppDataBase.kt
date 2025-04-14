package com.example.schooldiary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Subject::class, Lesson::class, Grade::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun lessonDao(): LessonDao
    abstract fun gradeDao(): GradeDao
}