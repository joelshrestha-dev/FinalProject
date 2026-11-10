package com.example.finalproject.ui.linklist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.model.Link
import com.example.finalproject.ui.LinkBox
import com.example.finalproject.ui.theme.FinalProjectTheme

@ExperimentalFoundationApi
@Composable
fun LinkListView(
    modifier: Modifier = Modifier,
    links: List<Link>,
    selectedLink: Link? = null,
    onDelete: (Link) -> Unit,
    onSelectLink: (Link) -> Unit,
    waiting: Boolean = false,
    showDialogue: Boolean = false,
    onSetShowDialogue: (Boolean) -> Unit = {}

) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {
        val handleRowDelete: (Link) -> Unit = { link ->
            onSelectLink(link)
            onSetShowDialogue(true)
        }

        val configuration = LocalConfiguration.current
        val clipboardManager = LocalClipboard.current

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Landscape(
                modifier = Modifier.alpha(if (waiting) 0.2f else 1.0f),
                links = links,
                selectedLink = selectedLink,
                onDelete = handleRowDelete,
                onSelectLink = onSelectLink,
            )
        } else {
            Portrait(
                modifier = Modifier.alpha(if (waiting) 0.2f else 1.0f),
                links = links,
                onDelete = handleRowDelete,
                onSelectLink = onSelectLink
            )
        }

        if (showDialogue) {
            ConfirmDeletion(
                selectedLink = selectedLink,
                onConfirm = {
                    selectedLink?.let { onDelete(it) }
                    onSetShowDialogue(false)
                },
                onDismiss = {
                    onSetShowDialogue(false)
                }
            )
        }

        if (waiting) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ConfirmDeletion(
    selectedLink: Link?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Do you want to delete this link?") // TODO: make the text wrap a little cleaner
        },
        text = {
            Text(
                text = if(selectedLink != null) selectedLink.url else "",
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class) // TODO: ADD IN TOGGLEABLE GROUPS FOR LINK HISTORY ON A PER APP BASIS
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
fun Landscape ( // TODO: MOVE DELETE BUTTON TO THE SIDE CARD IN LANDSCAPE? OR A LANDSCAPE DELETE FUNCTION
    modifier: Modifier = Modifier,
    links: List<Link>,
    selectedLink: Link?,
    onDelete: (Link) -> Unit,
    onSelectLink: (Link) -> Unit
) {
    Row () {
        Card (
            modifier = Modifier
                .weight(1.0f)
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {


                    },
                contentAlignment = Alignment.Center
            ) {
                Text(selectedLink?.url ?: "") // TODO: potentially add embed details -> internet api + cache
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

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LinkListPreview () {
    FinalProjectTheme() {
        val vm = LinkListViewModel()
        LinkListView(
            modifier = Modifier,
            links = vm.links.value,
            selectedLink = null,
            waiting = false,
            onDelete = {},
            onSelectLink = {}
        )
    }
}