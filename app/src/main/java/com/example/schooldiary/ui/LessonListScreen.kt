package com.example.schooldiary.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.schooldiary.data.models.Lesson
import com.example.schooldiary.data.models.viewmodels.LessonViewModel
import com.example.schooldiary.extensions.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonListScreen(viewModel: LessonViewModel, navController: NavController) {
    // Получение списка уроков из ViewModel
    val lessons by viewModel.lessons.collectAsState(initial = emptyList())

    // Загрузка всех уроков при запуске экрана
    LaunchedEffect(Unit) {
        viewModel.loadAllLessons()
    }

    // Отображение экрана с использованием Scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lesson List") }
            )
        },
        content = { padding ->
            // LazyColumn для отображения списка уроков
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(lessons) { lesson ->
                    LessonItem(lesson = lesson)
                }
            }
        }
    )
}

@Composable
fun LessonItem(lesson: Lesson) {
    // Отображение отдельного урока в виде карточки
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Subject ID: ${lesson.subjectId}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Date: ${lesson.date}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Assignments: ${lesson.assignments}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Grades: ${lesson.grades}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}