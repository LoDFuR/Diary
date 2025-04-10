package com.example.schooldiary.ui

//package com.example.schooldiary.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController

fun NavGraph(navController: NavController) {
    NavHost(navController = navController, startDestination = "startDestination") {
        composable("startDestination") { /* Ваш экран */ }
        // Добавьте другие экраны здесь
    }
}