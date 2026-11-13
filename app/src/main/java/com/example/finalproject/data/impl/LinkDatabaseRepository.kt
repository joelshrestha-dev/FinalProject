package com.example.finalproject.data.impl

import android.app.Application
import androidx.room3.Room
import com.example.finalproject.data.ILinkRepository
import com.example.finalproject.data.LinksDatabase
import com.example.finalproject.data.model.Link
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.milliseconds

class LinkDatabaseRepository(app: Application) : ILinkRepository {

    private val linksDatabase: LinksDatabase

    init {
        linksDatabase = Room.databaseBuilder(
                context = app,
                klass = LinksDatabase::class.java,
                name = "links.db"
            ).fallbackToDestructiveMigration(true) // deletes the db on version change
            .build()
    }

    override suspend fun getLinks(): List<Link> {
        return linksDatabase.linksDao().getLinks()
    }

    override fun getLinksAsFlow(): Flow<List<Link>> {
        return linksDatabase.linksDao().getLinksAsFlow()
    }

    override suspend fun deleteLink(link: Link) {
        delay(2000.milliseconds)
        linksDatabase.linksDao().deleteLink(link)
    }

    override suspend fun addLink(link: Link) {
        linksDatabase.linksDao().addLink(link)
    }
}