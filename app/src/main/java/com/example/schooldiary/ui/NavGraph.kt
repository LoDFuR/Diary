package com.example.schooldiary.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

//package com.example.schooldiary.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.schooldiary.ui.AddSubjectScreen
import com.example.schooldiary.ui.MainFragment

@Composable
fun NavGraph(navController: NavController) {
    NavHost(navController = navController as NavHostController, startDestination = "main") {
        // Главный экран
        composable("main") {
            MainFragment(navController = navController)
        }
        // Экран добавления предмета
        composable("add_subject") {
            AddSubjectScreen(navController = navController)
        }
        // Добавьте другие экраны здесь
    }
}