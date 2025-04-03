package com.example.schooldiary.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun GreetingScreen(navController: NavController, modifier: Modifier = Modifier) {
    Button(onClick = { navController.navigate("subject_list_screen") }) {
        Text(text = "Go to Subject List")
    }
}