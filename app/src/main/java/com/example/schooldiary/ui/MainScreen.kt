package com.example.schooldiary.ui

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

//package com.example.schooldiary.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schooldiary.data.models.viewmodels.MainViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = viewModel()) {
    val subjects by mainViewModel.subjectsLiveData.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_subject_screen") }) {
                Text("+")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Список предметов", style = MaterialTheme.typography.h5)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(subjects) { subject ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { /* Реализуйте действие при клике на предмет */ },
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = subject.name, style = MaterialTheme.typography.h6)
                            if (subject.assignments.isNotEmpty()) {
                                Text(text = "Задания: ${subject.assignments}", style = MaterialTheme.typography.body2)
                            }
                        }
                    }
                }
            }
        }
    }
}