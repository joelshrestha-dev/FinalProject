package com.example.finalproject.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.model.Link

@ExperimentalFoundationApi
@Composable
fun LinkBox (
    link: Link,
    onDelete:(Link) -> Unit,
    onSelectLink:(Link) -> Unit
) {
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
                .clickable { onSelectLink(link) },
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Absolute.SpaceBetween
            ) { //  icon?] \[link itself]
                Text("[~~]") // input symbol here
                Text(link.url) // TODO: make link clickable
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Absolute.SpaceBetween
            ) { // [share button] \[copy button]
                Text("[~x]") // sharesheet button
                Text("[x~]") // copy button
            }

        }

    }
}