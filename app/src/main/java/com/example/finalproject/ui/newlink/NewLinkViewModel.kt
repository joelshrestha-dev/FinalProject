package com.example.finalproject.ui.newlink

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.model.Link

class NewLinkViewModel : ViewModel() {
    private val _url = mutableStateOf("")
    val url = _url

    fun setUrl(url: String) {
        _url.value = url
    }

    fun validate(): Link {
        if(url.value.isEmpty()) {
            throw Exception("URL cannot be empty")
        }

        // TODO: validate that it is actually a url, check against precontained list of usable websites stored in a seperate file

        return Link("",url.value)
    }
}