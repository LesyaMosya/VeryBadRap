package com.example.verybadrap.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.verybadrap.model.Round
import com.example.verybadrap.model.Team
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor() {
    var listOfTeams = mutableStateListOf<Team>()
    var listOfRounds = mutableStateListOf<Round>()

    var numberTeam = mutableIntStateOf(0)
    var numberRound = mutableIntStateOf(0)
    var numberSong = mutableIntStateOf(0)

}
