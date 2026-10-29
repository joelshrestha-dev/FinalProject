package com.example.finalproject.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.nav.LinkList
import com.example.finalproject.ui.nav.NavGraph

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
    TopAppBar(
        title = { Text("Link Correctional Fascility") },
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
            selected = currentDestination?.hasRoute(LinkList::class) ?: false,
            onClick = {
                nav.navigate(LinkList) {
                    launchSingleTop = true
                    popUpTo(LinkList)
                }
            },
            icon = {
                Icon(painterResource(R.drawable.ic_home), null)
            },
            label = {
                Text("SongList")
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
                Icon(painterResource(R.drawable.ic_add), null)
            },
            label = {
                Text("New Song")
            }
        )
    }
}