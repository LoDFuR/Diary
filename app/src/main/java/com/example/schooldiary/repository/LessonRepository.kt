package com.example.schooldiary.repository

import android.content.Context
import com.example.schooldiary.data.database.LessonDao
import com.example.schooldiary.data.models.Lesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class LessonRepository(private val lessonDao: LessonDao, private val context: Context) {

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

    suspend fun loadLessonsFromFile(): List<Lesson> = withContext(Dispatchers.IO) {
        val lessons = mutableListOf<Lesson>()
        val inputStream = context.assets.open("lessons.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                if (parts.size == 5) {
                    val lesson = Lesson(parts[0].toInt(), parts[1].toInt(), parts[2], parts[3], parts[4])
                    lessons.add(lesson)
                }
            }
        }
        lessons
    }
}