package com.example.schooldiary.repository

import android.content.Context
import com.example.schooldiary.data.database.SubjectDao
import com.example.schooldiary.data.models.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class SubjectRepository(private val subjectDao: SubjectDao, private val context: Context) {

    suspend fun insert(subject: Subject) {
        subjectDao.insert(subject)
    }

    suspend fun getAllSubjects(): List<Subject> {
        return subjectDao.getAllSubjects()
    }

    suspend fun update(subject: Subject) {
        subjectDao.update(subject)
    }

    suspend fun delete(subject: Subject) {
        subjectDao.delete(subject)
    }

    suspend fun getSubjectById(id: Int): Subject? {
        return subjectDao.getSubjectById(id)
    }
    suspend fun loadSubjectsFromFile(): List<Subject> = withContext(Dispatchers.IO) {
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