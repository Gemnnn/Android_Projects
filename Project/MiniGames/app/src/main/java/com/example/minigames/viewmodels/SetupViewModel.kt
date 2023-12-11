package com.example.minigames.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetupViewModel : ViewModel() {
    private val _playerName = MutableLiveData<String>()
    val playerName: LiveData<String> get() = _playerName

    private val _gameDifficulty = MutableLiveData<String>()
    val gameDifficulty: LiveData<String> get() = _gameDifficulty

    fun setPlayerName(name: String) {
        _playerName.value = name
    }

    fun setGameDifficulty(difficulty: String) {
        _gameDifficulty.value = difficulty
    }

    // You can add additional setup related functions here if needed
    // For example, validation of player name or handling different difficulty levels
}
