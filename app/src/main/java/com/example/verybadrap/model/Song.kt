package com.example.verybadrap.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Song")
data class Song(
    @PrimaryKey
    val id: Long = 0,

    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "difficult") val difficult: Int = 0
)
