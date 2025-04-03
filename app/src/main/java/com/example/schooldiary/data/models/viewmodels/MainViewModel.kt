package com.example.schooldiary.data.models.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiary.data.models.Subject
import com.example.schooldiary.repository.SubjectRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SubjectRepository) : ViewModel() {

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> get() = _subjects

    fun insert(subject: Subject) {
        viewModelScope.launch {
            repository.insert(subject)
        }
    }

    fun loadAllSubjects() {
        viewModelScope.launch {
            _subjects.value = repository.getAllSubjects()
        }
    }
}