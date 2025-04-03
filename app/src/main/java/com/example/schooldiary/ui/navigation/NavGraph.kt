package com.example.schooldiary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.schooldiary.ui.GreetingFragment
import com.example.schooldiary.ui.LessonListFragment
import com.example.schooldiary.ui.SubjectListFragment

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "greeting") {
        composable("greeting") { GreetingFragment() }
        composable("subjects") { SubjectListFragment() }
        composable("lessons") { LessonListFragment() }
    }
}