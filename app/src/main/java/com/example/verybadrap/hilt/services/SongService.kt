package com.example.verybadrap.hilt.services

import com.example.verybadrap.model.Round


interface SongService {
    suspend fun getRounds(countOfTeams : Int): MutableList<Round>
}