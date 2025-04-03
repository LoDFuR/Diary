package com.example.schooldiary.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    Column {
        Button(onClick = {
            navController.navigate("subjects")
        }) {
            Text(text = "Go to Subjects")
        }
        Button(onClick = {
            navController.navigate("lessons")
        }) {
            Text(text = "Go to Lessons")
        }
    }
}