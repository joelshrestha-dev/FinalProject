package com.example.finalproject.ui.linklist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.model.Link
import com.example.finalproject.ui.LinkBox

@ExperimentalFoundationApi
@Composable
fun LinkListView(
    modifier: Modifier = Modifier,
    links: List<Link>,
    selectedLink: Link?,
    waiting: Boolean,
    onDelete: (Link) -> Unit,
    onSelectLink: (Link) -> Unit

) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {
        val handleRowDelete: (Link) -> Unit = { link ->
            onSelectLink(link)
        }

        val configuration = LocalConfiguration.current

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Landscape(
                modifier = Modifier.alpha(if (waiting) 0.2f else 1.0f),
                links = links,
                selectedLink = selectedLink,
                onDelete = onDelete,
                onSelectLink = onSelectLink,
            )
        } else {
            Portrait(
                modifier = Modifier.alpha(if (waiting) 0.2f else 1.0f),
                links = links,
                onDelete = onDelete,
                onSelectLink = onSelectLink
            )
        }
        if (waiting) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LinkListUI (
    modifier: Modifier = Modifier,
    links: List<Link>,
    onDelete: (Link) -> Unit,
    onSelectLink: (Link) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(links) { link ->
            LinkBox(link,onDelete,onSelectLink)
        }
    }
}

@Composable
fun Portrait (
    modifier: Modifier = Modifier,
    links: List<Link>,
    onDelete: (Link) -> Unit,
    onSelectLink: (Link) -> Unit
) {
    Column () {
        LinkListUI(
            links = links,
            onDelete = onDelete,
            onSelectLink = onSelectLink,
            modifier = modifier
        )
    }
}

@Composable
fun Landscape (
    modifier: Modifier = Modifier,
    links: List<Link>,
    selectedLink: Link?,
    onDelete: (Link) -> Unit,
    onSelectLink: (Link) -> Unit
) {
    Row () {
        Card (
            modifier = Modifier.weight(1.0f).padding(16.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(selectedLink?.url ?: "") // TODO: potentially add embed details
            }
        }
        LinkListUI(
            links = links,
            onDelete = onDelete,
            onSelectLink = onSelectLink,
            modifier = modifier
        )

    }
}