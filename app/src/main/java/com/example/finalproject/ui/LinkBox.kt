package com.example.finalproject.ui

import android.R.attr.type
import android.content.ClipData
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.finalproject.R
import com.example.finalproject.data.model.Link
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun LinkBox (
    link: Link,
    onDelete:(Link) -> Unit,
    onSelectLink:(Link) -> Unit,

) {
    val clipboard = LocalClipboard.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Log.d("TAG", link.id)

    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start=16.dp, end=16.dp, top=5.dp, bottom=5.dp)
            .fillMaxWidth()
    ) {
        /*
        link box
		\[icon?] \[link itself]
		\[share button] \[copy button]
			share button: potentially not there, otherwise opens sharesheet
			copy button: copies to clipboard
		for preview -> all just icons
		*/
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onDelete(link) },
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Absolute.SpaceBetween
            ) { //  icon?] \[link itself]
                Text(
                    text = "[~~]",
                    modifier = Modifier.weight(0.3f)
                ) // TODO: can import symbols for various preloaded websites -> SQL, async -> return value, store one copy per app and reference per link
                Text(
                    text = link.url,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .basicMarquee()
                        .weight(0.7f)

                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Absolute.SpaceBetween
            ) { // [share button] \[copy button]
                Icon(
                    painterResource(
                        R.drawable.ic_share),
                    null,
                    modifier = Modifier.clickable {
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_TEXT, link.url)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                )

                Icon(
                    painterResource(
                        R.drawable.ic_content_copy),
                    null,
                    modifier = Modifier.clickable {
                        scope.launch {
                            clipboard.setClipEntry((ClipData.newPlainText("label", link.url).toClipEntry()))
                        }
                    }
                )
            }

        }

    }
}