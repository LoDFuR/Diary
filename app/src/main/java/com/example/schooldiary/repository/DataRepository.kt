package com.example.schooldiary.repository

import android.content.Context
import com.example.schooldiary.data.models.Lesson
import com.example.schooldiary.data.models.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class DataRepository(private val context: Context) {

    suspend fun loadLessons(): List<Lesson> = withContext(Dispatchers.IO) {
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

    suspend fun loadSubjects(): List<Subject> = withContext(Dispatchers.IO) {
        val subjects = mutableListOf<Subject>()
        val inputStream = context.assets.open("subjects.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                if (parts.size == 3) {
                    val subject = Subject(parts[0].toInt(), parts[1], parts[2].toDouble())
                    subjects.add(subject)
                }
            }
        }
        subjects
    }
}