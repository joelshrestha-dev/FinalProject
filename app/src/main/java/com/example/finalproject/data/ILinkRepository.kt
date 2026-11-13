package com.example.finalproject.data

import com.example.finalproject.data.model.Link
import kotlinx.coroutines.flow.Flow

interface ILinkRepository {
    suspend fun getLinks() : List<Link>
    fun getLinksAsFlow() : Flow<List<Link>>
    suspend fun deleteLink(link: Link)
    suspend fun addLink(link: Link)
}