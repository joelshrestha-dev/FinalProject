package com.example.finalproject.data.impl

import com.example.finalproject.data.ILinkRepository
import com.example.finalproject.data.model.Link
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.milliseconds

class LinkRepository : ILinkRepository {

    private var _links = listOf<Link>()

    init {
        _links =(0..20).map { i ->
            Link("Link $i", "https://www.google.com/$i")
        }
    }

    override suspend fun getLinks(): List<Link> {
        return _links
    }

    override fun getLinksAsFlow(): Flow<List<Link>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLink(link: Link) { // TODO: add a list / stack for "undo delete" button, clears upon app restarting -> stored in cache
        delay(500.milliseconds)
        _links = _links.filter { s -> s.id != link.id }
    }

    override suspend fun addLink(link: Link) { // TODO. adds to database, will need to be suspend
        delay(2000)
        _links = listOf(link) + _links
    }
}