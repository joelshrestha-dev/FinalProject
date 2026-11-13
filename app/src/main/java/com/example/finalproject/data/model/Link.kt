package com.example.finalproject.data.model

import androidx.room3.*
import java.util.UUID

@Entity(tableName = "links")
data class Link(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var url: String
)