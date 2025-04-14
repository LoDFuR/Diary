package com.example.schooldiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.schooldiary.data.database.GradeDao
import com.example.schooldiary.data.database.SubjectDao

class SubjectsViewModelFactory(
    private val subjectDao: SubjectDao,
    private val gradeDao: GradeDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectsViewModel::class.java)) {
            return SubjectsViewModel(subjectDao, gradeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}