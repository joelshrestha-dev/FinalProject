package com.example.finalproject.data.model

import androidx.room3.*

@Entity(tableName = "links")
data class Link(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // TODO: will need to change this to be auto generated within the db
    var url: String
)