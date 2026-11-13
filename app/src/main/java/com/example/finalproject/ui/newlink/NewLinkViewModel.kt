package com.example.finalproject.ui.newlink

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.model.Link
import java.net.URI
import java.net.URL
import android.widget.Toast
class NewLinkViewModel : ViewModel() {
    private val _url = mutableStateOf("")
    val url = _url

    fun setUrl(url: String) {
        _url.value = url
    }

    fun validate(): Link {
        if(url.value.isBlank()) {
            throw Exception("URL cannot be empty")
        }

        // TODO: validate that it is actually a url, check against precontained list of usable websites stored in a seperate file, async -> return value

        return try {
            URI(url.value).toURL()
            Link(0,url.value)
        } catch (e: Exception) {
            Toast.makeText(
                null,
                "Invalid URL",
                Toast.LENGTH_SHORT
            ).show()
            null
        }!!
    }
}