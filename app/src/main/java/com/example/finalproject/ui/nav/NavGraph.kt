package com.example.finalproject.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.internal.NavContext

@Composable
fun NavGraph(
    navController: NavHostController =rememberNavController(),
    modifier: Modifier = Modifier
) {
    val viewModel: ViewModel = viewModel()

    NavHost (
        navController = navController,
        startDestination = newLink,
        modifier = modifier
    ) {

    }
}