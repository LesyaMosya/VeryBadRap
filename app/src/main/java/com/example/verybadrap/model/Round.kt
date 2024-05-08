package com.example.verybadrap.model


data class Round(
    var difficult: Int = 0,
    var listSongs: List<Song> = emptyList()
)
