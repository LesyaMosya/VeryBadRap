package com.example.verybadrap.viewmodel

sealed class Event {
    data class AddTeam(val value: String) : Event()
    data class DeleteTeam(val value: String) : Event()
    data object StartGame : Event()
    data object NextRound : Event()
    data object FinishGame : Event()
    data object ReturnHome : Event()
}