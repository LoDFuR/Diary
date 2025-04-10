package com.example.schooldiary.data.models.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiary.data.database.AppDatabase
import com.example.schooldiary.data.database.Subject
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    // Получаем DAO из базы данных
    private val dao = AppDatabase.getDatabase(application).schoolDao()

    // LiveData для списка предметов
    private val _subjectsLiveData = MutableLiveData<List<Subject>>()
    val subjectsLiveData: LiveData<List<Subject>> get() = _subjectsLiveData

    init {
        // Загружаем данные при создании ViewModel
        loadSubjects()
    }

    // Метод для загрузки всех предметов
    fun loadSubjects() {
        viewModelScope.launch {
            try {
                val subjects = dao.getAllSubjects()
                _subjectsLiveData.postValue(subjects)
            } catch (e: Exception) {
                // Логирование ошибки
                e.printStackTrace()
            }
        }
    }

    // Метод для добавления нового предмета
    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            try {
                dao.insertSubject(subject)
                // Оптимизация: добавляем предмет в текущий список
                val currentSubjects = _subjectsLiveData.value.orEmpty().toMutableList()
                currentSubjects.add(subject)
                _subjectsLiveData.postValue(currentSubjects)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Метод для обновления существующего предмета
    fun updateSubject(subject: Subject) {
        viewModelScope.launch {
            try {
                dao.updateSubject(subject)
                // Оптимизация: обновляем предмет в текущем списке
                val currentSubjects = _subjectsLiveData.value.orEmpty().toMutableList()
                val index = currentSubjects.indexOfFirst { it.id == subject.id }
                if (index != -1) {
                    currentSubjects[index] = subject
                    _subjectsLiveData.postValue(currentSubjects)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Метод для удаления предмета
    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            try {
                dao.deleteSubject(subject)
                // Оптимизация: удаляем предмет из текущего списка
                val currentSubjects = _subjectsLiveData.value.orEmpty().toMutableList()
                currentSubjects.removeAll { it.id == subject.id }
                _subjectsLiveData.postValue(currentSubjects)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}