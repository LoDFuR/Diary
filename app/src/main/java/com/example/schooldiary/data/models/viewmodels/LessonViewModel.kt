package com.example.schooldiary.data.models.viewmodels

import androidx.lifecycle.*
import com.example.schooldiary.data.models.Lesson
import com.example.schooldiary.repository.LessonRepository
//import com.example.diary.model.Lesson
//import com.example.diary.repository.LessonRepository
import kotlinx.coroutines.launch

class LessonViewModel(private val repository: LessonRepository) : ViewModel() {

    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>> get() = _lessons

    init {
        loadAllLessons()
    }

    fun insert(lesson: Lesson) {
        viewModelScope.launch {
            repository.insert(lesson)
            loadAllLessons()
        }
    }

    fun update(lesson: Lesson) {
        viewModelScope.launch {
            repository.update(lesson)
            loadAllLessons()
        }
    }

    fun delete(lesson: Lesson) {
        viewModelScope.launch {
            repository.delete(lesson)
            loadAllLessons()
        }
    }

    fun loadAllLessons() {
        viewModelScope.launch {
            _lessons.value = repository.getAllLessons()
        }
    }

    fun getLessonsBySubject(subjectId: Int): LiveData<List<Lesson>> {
        val lessonsBySubject = MutableLiveData<List<Lesson>>()
        viewModelScope.launch {
            lessonsBySubject.value = repository.getLessonsBySubject(subjectId)
        }
        return lessonsBySubject
    }

    fun getAverageGrade(subjectId: Int): LiveData<Double> {
        val averageGrade = MutableLiveData<Double>()
        viewModelScope.launch {
            val lessons = repository.getLessonsBySubject(subjectId)
            val grades = lessons.flatMap { lesson ->
                lesson.grades.split(",").mapNotNull { it.toDoubleOrNull() }
            }
            averageGrade.value = grades.average()
        }
        return averageGrade
    }
}