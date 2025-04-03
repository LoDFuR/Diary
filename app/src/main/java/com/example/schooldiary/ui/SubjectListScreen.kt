package com.example.schooldiary.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.schooldiary.data.models.Subject
import com.example.schooldiary.data.models.viewmodels.SubjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectListScreen(navController: NavController, viewModel: SubjectViewModel) {
    // Получение списка предметов из ViewModel
    val subjects by viewModel.subjects.observeAsState(emptyList())

    // Загрузка всех предметов при запуске экрана
    LaunchedEffect(Unit) {
        viewModel.loadAllSubjects()
    }

    // Отображение экрана с использованием Scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subject List") },
                actions = {
                    IconButton(onClick = { navController.navigate("lessons") }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Go to Lesson List")
                    }
                }
            )
        },
        content = { padding ->
            // LazyColumn для отображения списка предметов
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(subjects) { subject ->
                    SubjectItem(subject = subject)
                }
            }
        }
    )
}

@Composable
fun SubjectItem(subject: Subject) {
    // Отображение отдельного предмета в виде карточки
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Subject ID: ${subject.id}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Name: ${subject.name}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Average Grade: ${subject.averageGrade}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}