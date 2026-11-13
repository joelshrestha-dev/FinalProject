package com.example.finalproject.data

import androidx.room3.*
import com.example.finalproject.data.model.Link
import kotlinx.coroutines.flow.Flow

@Dao
interface LinksDao {
    @Query("SELECT * FROM links") // TODO - PROPERLY FORMAT SELECT STATEMENT FROM MODEL
    suspend fun getLinks(): List<Link>

    @Query("SELECT * FROM links")
    fun getLinksAsFlow(): Flow<List<Link>>

    @Insert
    suspend fun addLink(link: Link)

    @Delete
    suspend fun deleteLink(link: Link)

    @Update
    suspend fun updateLink(link: Link)
}

@Database(entities = [Link::class], version = 1, exportSchema = false)
// for db reset -> adb shell pm clear com.example.finalproject
abstract class LinksDatabase : RoomDatabase() {
    abstract fun linksDao(): LinksDao
}