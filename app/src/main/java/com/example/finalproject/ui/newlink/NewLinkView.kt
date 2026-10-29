package com.example.finalproject.ui.newlink

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.data.model.Link
import com.example.finalproject.ui.theme.FinalProjectTheme
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun NewLinkView (
    vm: NewLinkViewModel = viewModel(),
    onAddLink: (Link) -> Unit
) {
    val ctx = LocalContext.current
    val urlTf: FocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(true) {
        urlTf.requestFocus()
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Clean Link",
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .focusRequester(urlTf)
        )

        OutlinedTextField(
            value = vm.url.value,
            onValueChange = vm::setUrl,
            placeholder = {
                Text("Url")
            },
            label = {
                Text("Url")
            },
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .focusRequester(urlTf),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = { keyboardController?.hide() }
            )
        )

        Button(
            onClick = {
                try {
                    val song = vm.validate()
                    // TODO: properly convert a song from tracker to non
                    onAddLink(song)
                } catch(e: Exception) {
                    Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Add Song")
        }
    }

}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FinalProjectTheme() {
        val vm = NewLinkViewModel()
        NewLinkView(vm, {})
    }
}