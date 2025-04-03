package com.example.schooldiary.repository

import android.content.Context
import com.example.schooldiary.data.database.SubjectDao
import com.example.schooldiary.data.models.Subject

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
}