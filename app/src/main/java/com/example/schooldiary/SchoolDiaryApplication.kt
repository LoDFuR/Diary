package com.example.schooldiary

import android.app.Application
import com.example.schooldiary.data.database.AppDatabase
import com.example.schooldiary.repository.SubjectRepository
import com.example.schooldiary.repository.LessonRepository

class SchoolDiaryApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val subjectRepository by lazy { SubjectRepository(database.subjectDao(), this) }
    val lessonRepository by lazy { LessonRepository(database.lessonDao(), this) }
}