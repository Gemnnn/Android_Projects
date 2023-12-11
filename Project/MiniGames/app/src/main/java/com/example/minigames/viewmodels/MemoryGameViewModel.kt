package com.example.minigames.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper
import com.example.minigames.models.Game
import com.example.minigames.models.Player

class MemoryGameViewModel : ViewModel() {
    private val game = Game()
    private val _player = MutableLiveData<Player>().apply { value = Player("", 0) }
    private val _highlightedTiles = MutableLiveData<List<Int>>()
    private val _gameOver = MutableLiveData<Boolean>()
    private var selectionTimerHandler = Handler(Looper.getMainLooper())
    private lateinit var selectionTimerRunnable: Runnable
    var isUserTurn = false

    val player: LiveData<Player> get() = _player
    val highlightedTiles: LiveData<List<Int>> get() = _highlightedTiles
    val gameOver: LiveData<Boolean> get() = _gameOver

    fun startGame(playerName: String, gameMode: String) {
        game.updateGameMode(gameMode)
        _player.value = Player(playerName, 0)
        _gameOver.value = true
        prepareNextRound()
    }

    private fun prepareNextRound() {
        game.generateRandomTiles()
        _highlightedTiles.value = game.getCorrectTiles()
        isUserTurn = true
        startSelectionTimer()
    }

    private fun startSelectionTimer() {
        selectionTimerRunnable = Runnable {
            if (isUserTurn) {
                _gameOver.postValue(true)
            }
            else{
                _gameOver.postValue(false)
            }
        }
        val delayMillis = if (game.gameMode == "Normal") 5000L else 4000L
        selectionTimerHandler.postDelayed(selectionTimerRunnable, delayMillis)
    }

    fun onTileSelected(selectedTiles: List<Int>) {
        if (!isUserTurn) return

        isUserTurn = false
        selectionTimerHandler.removeCallbacks(selectionTimerRunnable)

        val isCorrect = game.checkSelection(selectedTiles)
        if (isCorrect) {
            _player.value?.let {
                it.score = game.getCurrentScore()
                _player.postValue(it)
            }
            prepareNextRound()
        } else {
            _gameOver.postValue(false)
        }
    }

    fun getHighlightDuration(): Long {
        return if (game.gameMode == "Normal") 3000L else 5000L
    }

    fun getCurrentRound(): Int = game.getCurrentRound()
    fun getCurrentTilesToMemorize(): Int = game.getCurrentTilesToMemorize()
}
