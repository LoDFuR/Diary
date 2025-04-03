package com.example.schooldiary.repository

//import com.example.schedule.data.database.SubjectDao
//import com.example.schedule.data.models.Subject
import com.example.schooldiary.data.database.SubjectDao
import com.example.schooldiary.data.models.Subject

//import com.example.scheduule.models.Subject
//import com.example.scheduule.models.SubjectDao

class SubjectRepository(private val subjectDao: SubjectDao) {
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