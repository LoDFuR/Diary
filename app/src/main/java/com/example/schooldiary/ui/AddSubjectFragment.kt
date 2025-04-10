package com.example.schooldiary.ui
//package com.example.schooldiary.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainFragment(navController = navController)
        }
        composable(route = Screen.AddSubject.route) {
            AddSubjectScreen(navController = navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object AddSubject : Screen("add_subject_screen")
}