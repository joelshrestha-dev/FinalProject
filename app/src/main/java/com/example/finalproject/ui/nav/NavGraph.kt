package com.example.finalproject.ui.nav

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    navController: NavHostController =rememberNavController(),
    modifier: Modifier = Modifier
) {
    val viewModel: ViewModel = viewModel()

    NavHost (
        navController = navController,
        startDestination = NewLink,
        modifier = modifier
    ) {
        composable<LinkList> {
            
        }
    }
}