package com.example.finalproject.ui.linklist

import android.app.Application
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.ILinkRepository
import com.example.finalproject.data.impl.LinkDatabaseRepository
import com.example.finalproject.data.impl.LinkRepository
import com.example.finalproject.data.model.Link
import com.example.finalproject.network.ILinkFetcher
import com.example.finalproject.network.LinkFetcher
import kotlinx.coroutines.launch
import kotlin.collections.listOf

class LinkListViewModel(app: Application) : AndroidViewModel(app) {
    private val _links: MutableState<List<Link>> = mutableStateOf(listOf())
    val links = _links

    private val _selected: MutableState<Link?>
    val selectedLink: State<Link?>
    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>
    private val _showDialogue: MutableState<Boolean>
    val showDialogue: State<Boolean>

    private val _repository: ILinkRepository = LinkDatabaseRepository(app)
    private val _linkFetcher: ILinkFetcher = LinkFetcher()

    init {
        viewModelScope.launch {
            var linksFromApi = listOf<Link>()
            try {
                linksFromApi = _linkFetcher.fetchLinks()
            } catch (e: Exception) {
                Log.e("LinkListViewModel", "Error fetching links from API", e)
            }

            _links.value = _repository.getLinks()
            if(_links.value.size == 0) {
                linksFromApi.forEach { link ->
                    _repository.addLink(link)
                }
            }

            _repository.getLinksAsFlow().collect { links ->
                _links.value = links
            }
        }
//        _links.value = _repository.getLinks()
        _selected = mutableStateOf(null)
        selectedLink = _selected
        _waiting = mutableStateOf(false)
        waiting = _waiting
        _showDialogue = mutableStateOf(false)
        showDialogue = _showDialogue
    }

    fun addLink (link: Link) {
        viewModelScope.launch {
            _waiting.value = true
            _repository.addLink(link)
//            _links.value = _repository.getLinks()
            _waiting.value = false
        }
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