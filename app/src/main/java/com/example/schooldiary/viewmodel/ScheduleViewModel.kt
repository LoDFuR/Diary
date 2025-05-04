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

data class SubjectWithAverageGrade(
    val subject: Subject,
    val averageGrade: Float
)

class ScheduleViewModel(
    private val subjectDao: SubjectDao,
    private val lessonDao: LessonDao
) : ViewModel() {

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    private val _dailyLessons = MutableLiveData<List<Lesson>>()
    val dailyLessons: LiveData<List<Lesson>> = _dailyLessons

    private val _subjectsWithGrades = MutableLiveData<List<SubjectWithAverageGrade>>()
    val subjectsWithGrades: LiveData<List<SubjectWithAverageGrade>> = _subjectsWithGrades

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

    fun loadSubjectsWithAverageGrades() {
        viewModelScope.launch {
            val subjects = subjectDao.getAll()
            val subjectsWithGrades = subjects.map { subject ->
                val average = calculateAverageGrade(subject.id)
                SubjectWithAverageGrade(subject, average)
            }
            _subjectsWithGrades.value = subjectsWithGrades
        }
    }

    suspend fun addSubject(name: String, quarter: Int) {
        val subject = Subject(name = name, quarter = quarter)
        subjectDao.insert(subject)
        loadSchedule(quarter)
    }

    suspend fun updateSubject(subject: Subject) {
        subjectDao.update(subject)
        loadSchedule(subject.quarter)
    }

    suspend fun deleteSubject(subject: Subject) {
        subjectDao.delete(subject)
        loadSchedule(subject.quarter)
    }

    suspend fun addLesson(subjectId: Long, dayOfWeek: Int, homework: String?, grade: Int?) {
        val lesson = Lesson(subjectId = subjectId, dayOfWeek = dayOfWeek, homework = homework, grade = grade)
        lessonDao.insert(lesson)
        loadDayLessons(dayOfWeek)
    }

    suspend fun updateLesson(lesson: Lesson) {
        lessonDao.update(lesson)
        loadDayLessons(lesson.dayOfWeek)
    }

    suspend fun deleteLesson(lesson: Lesson) {
        lessonDao.delete(lesson)
        loadDayLessons(lesson.dayOfWeek)
    }

    fun calculateAverageGrade(subjectId: Long): Float {
        return runBlocking {
            val lessons = lessonDao.getLessonsBySubject(subjectId)
            val grades = lessons.filter { it.grade != null }.map { it.grade!! }
            if (grades.isEmpty()) 0f else grades.average().toFloat()
        }
    }

    suspend fun subjectExists(subjectId: Long): Boolean {
        return subjectDao.getSubjectById(subjectId) != null
    }
}