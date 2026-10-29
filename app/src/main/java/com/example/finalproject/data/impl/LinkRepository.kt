package com.example.finalproject.data.impl

import com.example.finalproject.data.ILinkRepository
import com.example.finalproject.data.model.Link
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class LinkRepository : ILinkRepository {
    private var _links = listOf<Link>()

    init {
        _links =(0..20).map { i ->
            Link("Link $i", "https://www.google.com")
        }
    }

    override fun getLinks(): List<Link> {
        return _links
    }

    override suspend fun deleteLink(link: Link) {
        delay(500.milliseconds)
        _links = _links.filter { s -> s.id != link.id }
    }

    override fun addLink(link: Link) {
        _links = listOf(link) + _links
    }
}