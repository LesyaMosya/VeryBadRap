package com.example.verybadrap.hilt.impl

import com.example.verybadrap.database.DatabaseRepository
import com.example.verybadrap.model.Round
import com.example.verybadrap.model.Song
import com.example.verybadrap.hilt.services.SongService
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongServiceImpl @Inject constructor(
    private val repository: DatabaseRepository
): SongService {

    override suspend fun getRounds(countOfTeams : Int) : MutableList<Round>
    {
        var listOfSongs : List<Song>
        val listOfRounds = mutableListOf<Round>()

        for (i in 1..3) {
            listOfSongs = repository.getListOfSongs(i, countOfTeams).first()
            listOfRounds.add(Round(i, listOfSongs))
        }

        return listOfRounds
    }


}