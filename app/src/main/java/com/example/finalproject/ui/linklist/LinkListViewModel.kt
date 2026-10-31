package com.example.finalproject.ui.linklist

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.ILinkRepository
import com.example.finalproject.data.impl.LinkRepository
import com.example.finalproject.data.model.Link
import kotlinx.coroutines.launch
import kotlin.collections.listOf

class LinkListViewModel : ViewModel() {
    private val _links: MutableState<List<Link>> = mutableStateOf(listOf())
    val links = _links

    private val _selected: MutableState<Link?>
    val selectedLink: State<Link?>

    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>
    private val _showDialogue: MutableState<Boolean>
    val showDialogue: State<Boolean>

    private val _repository: ILinkRepository = LinkRepository()

    init {
        _links.value = _repository.getLinks()
        _selected = mutableStateOf(null)
        selectedLink = _selected
        _waiting = mutableStateOf(false)
        waiting = _waiting
        _showDialogue = mutableStateOf(false)
        showDialogue = _showDialogue
        Log.d("SHOWDIALOGUETRACKER", showDialogue.value.toString())
    }

    fun addLink (link: Link) {
        _repository.addLink(link)
        _links.value = _repository.getLinks()
    }

    fun deleteLink (link: Link) {
        _waiting.value = true
        viewModelScope.launch {
            _repository.deleteLink(link)
            _links.value = _repository.getLinks()
            _waiting.value = false
        }
    }

    fun setShowDialogue(show: Boolean) {
        _showDialogue.value = show
    }

    fun selectLink (link: Link) {
        _selected.value = link
    }
}