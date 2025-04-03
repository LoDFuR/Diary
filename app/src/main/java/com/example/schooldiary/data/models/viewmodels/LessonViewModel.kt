package com.example.schooldiary.data.models.viewmodels

import androidx.lifecycle.*
import com.example.schooldiary.data.models.Lesson
import com.example.schooldiary.repository.LessonRepository
import kotlinx.coroutines.launch

class LessonViewModel(private val repository: LessonRepository) : ViewModel() {

    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>> get() = _lessons

    init {
        loadAllLessons()
    }

    fun loadAllLessons() {
        viewModelScope.launch {
            _lessons.value = repository.loadLessonsFromFile()
        }
    }
}