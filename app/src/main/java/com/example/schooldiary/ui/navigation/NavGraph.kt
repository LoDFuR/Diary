package com.example.schooldiary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.schooldiary.ui.HomeScreen
import com.example.schooldiary.ui.GreetingFragment
import com.example.schooldiary.ui.LessonListFragment
import com.example.schooldiary.ui.SubjectListFragment

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("greeting") { GreetingFragment(navController) }
        composable("subjects") { SubjectListFragment() }
        composable("lessons") { LessonListFragment(navController) }
    }
}