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
import com.example.finalproject.data.model.Link
import com.example.finalproject.ui.linklist.LinkListView
import com.example.finalproject.ui.linklist.LinkListViewModel
import com.example.finalproject.ui.newlink.NewLinkView

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalFoundationApi
@Composable
fun NavGraph(
    navController: NavHostController =rememberNavController(),
    modifier: Modifier = Modifier
) {
    val viewModel: LinkListViewModel = viewModel()

    NavHost (
        navController = navController,
        startDestination = NewLink,
        modifier = modifier
    ) {
        composable<LinkList> {
            val likList by viewModel.links
            val selectedLink by viewModel.selectedLink
            val waiting by viewModel.waiting
            val showDialogue by viewModel.showDialogue
            LinkListView(
                links = likList,
                selectedLink = selectedLink,
                waiting = waiting,
                showDialogue = showDialogue,
                onSetShowDialogue = viewModel::setShowDialogue,
                onDelete = viewModel::deleteLink,
                onSelectLink = viewModel::selectLink
            )
        }

        composable<NewLink> {
            NewLinkView(
                onAddLink = { link: Link ->
                    viewModel.addLink(link)
                    navController.navigate(LinkList) {
                        popUpTo(NewLink) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}