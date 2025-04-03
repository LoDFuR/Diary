package com.example.schooldiary.data.models.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.schedule.data.models.Subject
import com.example.schooldiary.repository.SubjectRepository
import com.example.schooldiary.data.models.Subject
//import com.example.schooldiary.model.Subject
//import com.example.schooldiary.repository.SubjectRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.*
//import com.example.diary.model.Subject
//import com.example.diary.repository.SubjectRepository
import kotlinx.coroutines.launch

class SubjectViewModel(private val repository: SubjectRepository) : ViewModel() {

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> get() = _subjects

    init {
        loadAllSubjects()
    }

    fun insert(subject: Subject) {
        viewModelScope.launch {
            repository.insert(subject)
            loadAllSubjects()
        }
    }

    fun update(subject: Subject) {
        viewModelScope.launch {
            repository.update(subject)
            loadAllSubjects()
        }
    }

    fun delete(subject: Subject) {
        viewModelScope.launch {
            repository.delete(subject)
            loadAllSubjects()
        }
    }

    fun loadAllSubjects() {
        viewModelScope.launch {
            _subjects.value = repository.getAllSubjects()
        }
    }

    fun getAverageGrade(subjectId: Int): LiveData<Double> {
        val averageGrade = MutableLiveData<Double>()
        viewModelScope.launch {
            val subjects = repository.getSubjectById(subjectId)
            averageGrade.value = subjects?.averageGrade ?: 0.0
        }
        return averageGrade
    }
}