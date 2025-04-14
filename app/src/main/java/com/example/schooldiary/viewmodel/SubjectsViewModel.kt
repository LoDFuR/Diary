package com.example.schooldiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiary.data.database.Grade
import com.example.schooldiary.data.database.GradeDao
import com.example.schooldiary.data.database.Subject
import com.example.schooldiary.data.database.SubjectDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SubjectWithGrades(
    val subject: Subject,
    val grades: List<Grade>
)

class SubjectsViewModel(
    private val subjectDao: SubjectDao,
    private val gradeDao: GradeDao
) : ViewModel() {
    private val _subjectsWithGrades = MutableStateFlow<List<SubjectWithGrades>>(emptyList())
    val subjectsWithGrades: StateFlow<List<SubjectWithGrades>> = _subjectsWithGrades.asStateFlow()

    init {
        loadSubjectsWithGrades()
    }

    private fun loadSubjectsWithGrades() {
        viewModelScope.launch {
            val subjects = subjectDao.getAll()
            val subjectsWithGrades = subjects.map { subject ->
                val grades = gradeDao.getGradesForSubject(subject.id)
                SubjectWithGrades(subject, grades)
            }
            _subjectsWithGrades.value = subjectsWithGrades
        }
    }
}