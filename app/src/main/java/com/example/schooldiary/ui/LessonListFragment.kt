package com.example.schooldiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import com.example.schooldiary.data.models.viewmodels.LessonViewModel
import com.example.schooldiary.data.models.viewmodels.LessonViewModelFactory
import com.example.schooldiary.repository.LessonRepository
import com.example.schooldiary.data.database.AppDatabase
import com.example.schooldiary.extensions.collectAsState

class LessonListFragment(navController: NavHostController) : Fragment() {

    private val lessonViewModel: LessonViewModel by viewModels {
        LessonViewModelFactory(LessonRepository(AppDatabase.getDatabase(requireContext()).lessonDao(), requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LessonListScreen(lessonViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonListScreen(viewModel: LessonViewModel) {
    val lessons by viewModel.lessons.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadAllLessons()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lesson List") }
            )
        },
        content = { padding ->
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
/*
@Composable
fun LessonItem(lesson: Lesson) {
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

 */