package com.example.schooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiary.data.database.Lesson
import com.example.schooldiary.data.database.LessonDao
import com.example.schooldiary.data.database.Subject
import com.example.schooldiary.data.database.SubjectDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ScheduleViewModel(
    private val subjectDao: SubjectDao,
    private val lessonDao: LessonDao
) : ViewModel() {

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    private val _dailyLessons = MutableLiveData<List<Lesson>>()
    val dailyLessons: LiveData<List<Lesson>> = _dailyLessons

    fun loadSchedule(quarter: Int) {
        viewModelScope.launch {
            _subjects.value = subjectDao.getSubjectsByQuarter(quarter)
        }
    }

    fun loadDayLessons(dayOfWeek: Int) {
        viewModelScope.launch {
            _dailyLessons.value = lessonDao.getLessonsByDay(dayOfWeek)
        }
    }

    suspend fun addSubject(name: String, quarter: Int) {
        val subject = Subject(name = name, quarter = quarter)
        subjectDao.insert(subject)
        loadSchedule(quarter)
    }

    suspend fun deleteSubject(subject: Subject) {
        subjectDao.delete(subject)
        loadSchedule(subject.quarter)
    }

    suspend fun addLesson(subjectId: Long, dayOfWeek: Int, homework: String?, grade: Int?) { // Changed from Int to Long
        val lesson = Lesson(subjectId = subjectId, dayOfWeek = dayOfWeek, homework = homework, grade = grade)
        lessonDao.insert(lesson)
        loadDayLessons(dayOfWeek)
    }

    fun calculateAverageGrade(subjectId: Long): Float { // Changed from Int to Long
        return runBlocking {
            val lessons = lessonDao.getLessonsBySubject(subjectId)
            val grades = lessons.filter { it.grade != null }.map { it.grade!! }
            if (grades.isEmpty()) 0f else grades.average().toFloat()
        }
    }
}