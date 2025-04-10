package com.example.schooldiary.data.models.viewmodels

import com.example.schooldiary.data.database.AppDatabase
import com.example.schooldiary.data.database.Subject

//package com.example.schooldiary.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.schooldiary.data.AppDatabase
//import com.example.schooldiary.data.Subject
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    private val dao = AppDatabase.getDatabase(application).schoolDao()

    private val _subjectsLiveData = MutableLiveData<List<Subject>>()
    val subjectsLiveData: LiveData<List<Subject>> get() = _subjectsLiveData

    fun loadSubjects() {
        viewModelScope.launch {
            _subjectsLiveData.postValue(dao.getAllSubjects())
        }
    }

    // Другие методы для работы с уроками и оценками...
}