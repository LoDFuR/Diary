package com.example.schooldiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.schooldiary.data.database.LessonDao
import com.example.schooldiary.data.database.SubjectDao

class ScheduleViewModelFactory(
    private val subjectDao: SubjectDao,
    private val lessonDao: LessonDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(subjectDao, lessonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}