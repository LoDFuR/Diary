package com.example.schooldiary.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun GreetingFragment(navController: NavHostController) {
    Text(text = "Welcome to the School Diary app!")
}