package com.example.finalproject.data

import com.example.finalproject.data.model.Link

interface ILinkRepository {
    fun getLinks() : List<Link>
    suspend fun deleteLink(link: Link)
    fun addLink(link: Link)
}