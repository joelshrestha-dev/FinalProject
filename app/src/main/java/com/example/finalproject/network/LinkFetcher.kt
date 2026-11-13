package com.example.finalproject.network

import android.graphics.Bitmap
import com.example.finalproject.data.model.Link
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.create
import java.io.InputStream

interface ILinkFetcher {
    suspend fun fetchLinks(): List<Link>

    suspend fun fetchIcon(url: String):Bitmap?
}

interface LinkApi {
    @GET("links")
    suspend fun getLinks(): List<Link>
}


class LinkFetcher: ILinkFetcher {
    private val URL = "http://10.0.2.2:3000/"

    private val _linkApi: LinkApi

    init {
        _linkApi = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LinkApi::class.java)
    }

    private suspend fun fetchBytes(url: String): InputStream? {
        TODO("Implement fetchBytes")
    }

    override suspend fun fetchLinks(): List<Link> {
        try{
            return withContext(Dispatchers.IO) { _linkApi.getLinks() } //TODO - MAKE THE API
        } catch (e: Exception) {
            return listOf(
                Link(url = "Error:  " + e.toString())
            )
        }
    }

    override suspend fun fetchIcon(url: String): Bitmap? {
        TODO("Not yet implemented")
    }
}