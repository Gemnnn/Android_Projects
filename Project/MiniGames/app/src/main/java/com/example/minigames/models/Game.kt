package com.example.minigames.models

class Game {
    private val gridSize = 6
    private var currentRound = 1
    private var currentScore = 0
    private var tilesToHighlight = 4
    private var correctTiles: List<Int> = emptyList()
    var gameMode = "Normal"

    fun generateRandomTiles() {
        correctTiles = (0 until gridSize * gridSize).shuffled().take(tilesToHighlight)
    }

    fun updateGameMode(mode: String) {
        gameMode = mode
        // Any additional logic needed when game mode is updated
    }

    fun checkSelection(selectedTiles: List<Int>): Boolean {
        val isCorrect = if (gameMode == "Normal") {
            selectedTiles.toSet() == correctTiles.toSet()
        } else {
            selectedTiles == correctTiles
        }

        if (isCorrect) {
            updateScoreAndRound()
        } else {
            resetGame()
        }

        return isCorrect
    }

    private fun updateScoreAndRound() {
        val scoreIncrement = if (gameMode == "Normal") 10 else 20
        currentScore += scoreIncrement

        if (currentRound % 3 == 0) {
            tilesToHighlight += if (gameMode == "Normal") 1 else 2
        }

        currentRound++
    }

    private fun resetGame() {
        currentRound = 1
        currentScore = 0
        tilesToHighlight = if (gameMode == "Normal") 4 else 6
        correctTiles = emptyList()
    }

    fun calculateScore(): Int {
        return if (gameMode == "Normal") 10 else 20
    }


    fun getCurrentScore(): Int = currentScore
    fun getCurrentRound(): Int = currentRound
    fun getCurrentTilesToMemorize(): Int = tilesToHighlight
    fun getCorrectTiles(): List<Int> = correctTiles
}
