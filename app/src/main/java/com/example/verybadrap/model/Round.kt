package com.example.verybadrap.model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class Round(
    var difficult: Int = 0,
    var listSongs: List<Song> = emptyList()
)
