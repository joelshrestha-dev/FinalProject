package com.example.finalproject.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import com.example.finalproject.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.nav.LinkList
import com.example.finalproject.ui.nav.NavGraph
import com.example.finalproject.ui.nav.NewLink

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainScreen() {
    val nav = rememberNavController()
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(nav = nav)
        }
    ) { pv: PaddingValues ->
        NavGraph(nav, modifier = Modifier.padding(pv))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = { Text("Link Correctional Facility") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
private fun BottomBar(
    nav: NavHostController
) {
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    NavigationBar() {
        NavigationBarItem(
            selected = currentDestination?.hasRoute(NewLink::class) ?: false,
            onClick = {
                nav.navigate(NewLink) {
                    launchSingleTop = true
                    popUpTo(NewLink)
                }
            },
            icon = {
                Icon(painterResource(R.drawable.ic_add), null)
            },
            label = {
                Text("New Link")
            }
        )

        NavigationBarItem(
            selected = currentDestination?.hasRoute(LinkList::class) ?: false,
            onClick = {
                nav.navigate(LinkList) {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(painterResource(R.drawable.ic_history), null)
            },
            label = {
                Text("Link History")
            }
        )

    }
}