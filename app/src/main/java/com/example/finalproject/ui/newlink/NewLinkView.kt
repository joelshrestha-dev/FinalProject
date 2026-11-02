package com.example.finalproject.ui.newlink

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.data.model.Link
import com.example.finalproject.ui.theme.FinalProjectTheme
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.URI

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun NewLinkView (
    vm: NewLinkViewModel = viewModel(),
    onAddLink: (Link) -> Unit
) {
    val context = LocalContext.current
    val urlTf: FocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val clipboard = LocalClipboard.current // clipboardManager.setText(AnnotatedString(textToCopy))
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        urlTf.requestFocus()
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
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

        // TODO: ADD POTENTIAL FOR ALTERING ACCEPTABLE LINKS

        Button(
            onClick = {
                try {
                    val link: Link = vm.validate()

                    val uri = URI(link.url)

                    link.url = URI(uri.scheme, uri.host, uri.path, null, null).toString()

                    scope.launch {
                        clipboard.setClipEntry(
                            ClipData.newPlainText(
                                "label",
                                link.url.toString()
                            ).toClipEntry()
                        )
                    }
                    onAddLink(link)
                } catch(e: Exception) {
                    Toast.makeText( // TODO: make this toast better, snackbar would be better
                        context,
                        "INVALID URL",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Text("Clean")
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