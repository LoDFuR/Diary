package com.example.schooldiary.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
//import androidx.compose.material.Button
//import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schooldiary.data.database.Subject
import com.example.schooldiary.data.models.viewmodels.MainViewModel

@Composable
fun AddSubjectScreen(navController: NavController, mainViewModel: MainViewModel = viewModel()) {
    var subjectName by remember { mutableStateOf(TextFieldValue("")) }
    var assignments by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Добавить предмет", modifier = Modifier.padding(bottom = 16.dp))

        BasicTextField(
            value = subjectName,
            onValueChange = { subjectName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxWidth()) {
                    if (subjectName.text.isEmpty()) {
                        Text(text = "Название предмета", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = assignments,
            onValueChange = { assignments = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxWidth()) {
                    if (assignments.text.isEmpty()) {
                        Text(text = "Задания", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        Button(
            onClick = {
                if (subjectName.text.isNotBlank()) {
                    mainViewModel.addSubject(Subject(name = subjectName.text, assignments = assignments.text))
                    Toast.makeText(navController.context, "Предмет добавлен!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack() // Возврат на предыдущий экран
                } else {
                    Toast.makeText(navController.context, "Введите название предмета", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Добавить")
        }
    }
}