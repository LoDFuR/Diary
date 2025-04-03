package com.example.schooldiary.repository;

import com.example.schooldiary.data.database.LessonDao
import com.example.schooldiary.data.models.Lesson

//import com.example.diary.model.Lesson
//import com.example.diary.model.LessonDao

class LessonRepository(private val lessonDao: LessonDao) {

    suspend fun insert(lesson: Lesson) {
        lessonDao.insert(lesson)
    }

    suspend fun update(lesson: Lesson) {
        lessonDao.update(lesson)
    }

    suspend fun delete(lesson: Lesson) {
        lessonDao.delete(lesson)
    }

    suspend fun getAllLessons(): List<Lesson> {
        return lessonDao.getAllLessons()
    }

    suspend fun getLessonsBySubject(subjectId: Int): List<Lesson> {
        return lessonDao.getLessonsBySubject(subjectId)
    }
}