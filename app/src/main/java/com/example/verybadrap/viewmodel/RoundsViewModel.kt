package com.example.verybadrap.viewmodel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verybadrap.model.Round
import com.example.verybadrap.hilt.services.SongService
import com.example.verybadrap.hilt.services.TextService
import com.example.verybadrap.model.Song
import com.example.verybadrap.model.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class RoundsViewModel @Inject constructor(
    private val songServiceImpl: SongService,
    private val textServiceImpl: TextService,
    private val gameRepository: GameRepository
) : ViewModel() {

    val ifFullListOfTeams = gameRepository.ifFullListOfTeams
    val isBeginning = gameRepository.isBeginning.value
    val listOfTeams = gameRepository.listOfTeams
    val isEnding : State<Boolean> = gameRepository.isEnding

    private val _currentTeam = mutableStateOf(Team())
    val currentTeam : State<Team> = _currentTeam

    private val _currentRound = mutableStateOf(Round())
    val currentRound : State<Round> = _currentRound

    private var _currentSong = mutableStateOf(Song())
    val currentSong = _currentSong

    private val _currentTextOfSong = mutableStateOf("")
    val currentTextOfSong : MutableState<String> = _currentTextOfSong



    fun createEvent(event: Event) {
        onEvent(event)
    }

    private fun onEvent(event: Event) {
        when (event) {
            is Event.AddTeam -> {
                gameRepository.listOfTeams.add(Team(event.value))

                gameRepository.ifFullListOfTeams.value = gameRepository.listOfTeams.size == 3
            }
            is Event.DeleteTeam -> {
                gameRepository.listOfTeams.remove(Team(event.value))

                gameRepository.ifFullListOfTeams.value = gameRepository.listOfTeams.size == 3
            }
            is Event.StartGame -> {
                if (gameRepository.listOfTeams.isEmpty()) {
                    gameRepository.listOfTeams.add(Team("Количество баллов"))
                }

                loadRounds(gameRepository.listOfTeams.size)
                _currentTeam.value = gameRepository.listOfTeams[gameRepository.numberTeam.intValue]
            }
            is Event.NextRound -> {

                if (
                    gameRepository.listOfTeams.size != 1 &&
                    gameRepository.numberTeam.intValue < gameRepository.listOfTeams.size-1
                    ) {
                    gameRepository.numberTeam.intValue++
                    gameRepository.numberSong.intValue++
                } else {
                    gameRepository.numberRound.intValue++
                    gameRepository.numberTeam.intValue = 0
                    gameRepository.numberSong.intValue = 0
                }

                if (gameRepository.numberRound.intValue < 3) {
                    _currentRound.value =
                        gameRepository.listOfRounds[gameRepository.numberRound.intValue]
                    _currentTeam.value =
                        gameRepository.listOfTeams[gameRepository.numberTeam.intValue]
                    _currentSong.value =
                        _currentRound.value.listSongs[gameRepository.numberSong.intValue]
                }
                if (gameRepository.numberRound.intValue == 2)
                    createEvent(Event.FinishGame)
            }
            is Event.ReturnHome -> {
                gameRepository.listOfRounds.clear()
                gameRepository.listOfTeams.clear()

                gameRepository.isBeginning.value = false
                gameRepository.isEnding.value = false


                gameRepository.numberRound.intValue = 0
                gameRepository.numberSong.intValue = 0
                gameRepository.numberTeam.intValue = 0

                _currentRound.value = Round()
                _currentTeam.value = Team()
                _currentSong.value = Song()
                _currentTextOfSong.value = ""
            }
            is Event.FinishGame -> {
                gameRepository.isEnding.value = true
            }
        }
    }

    private fun loadRounds(countOfTeams : Int)
    {
        viewModelScope.launch {
            gameRepository.listOfRounds.addAll(songServiceImpl.getRounds(countOfTeams))
            _currentRound.value = gameRepository.listOfRounds[gameRepository.numberRound.intValue]
            _currentSong.value = _currentRound.value.listSongs[gameRepository.numberSong.intValue]
            gameRepository.isBeginning.value = true
        }

    }

    fun checkingText(enteredText: String) : MutableMap<Int, Int> {

        val path = "lyrics/" + _currentSong.value.title + ".txt"
        _currentTextOfSong.value = textServiceImpl.readingTextFile(path)

        val listOfWords = enteredText.trim()
            .split('.', ',', '—', ' ', '?')
            .filter { it.isNotBlank() }
            .toList()

        val listIndices = mutableMapOf<Int, Int>()
        var score = 0.0
        for (word in listOfWords) {

            val regex = """(?i)\b$word\b""".toRegex()
            val foundIndices = regex.findAll(_currentTextOfSong.value)
                .map { it.range.first }
                .toList()

            if (foundIndices.isNotEmpty()) {
                for (index in foundIndices) {
                    listIndices[index] = word.length
                }
                score += foundIndices.size
            }
        }
        val percentageOfGuessedWords = score / computeCountWordsInText() * 100
        _currentTeam.value.score += percentageOfGuessedWords.roundToInt() * _currentSong.value.difficult

        return listIndices
    }

    private fun computeCountWordsInText() : Int {

        var words = _currentTextOfSong.value.split("?", " ", ".", ",", "—")
        words =  words.filter { it.isNotBlank() }
        return words.size
    }
}
