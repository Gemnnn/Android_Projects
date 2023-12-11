package com.example.minigames.models

class Score {
    private val scores: MutableList<Player> = mutableListOf()

    fun addScore(player: Player) {
        val existingPlayer = scores.find { it.name == player.name }

        if (existingPlayer != null) {
            if (player.score > existingPlayer.score) {
                scores.remove(existingPlayer)
                scores.add(player)
            }
        } else {
            // Add new player
            scores.add(player)
        }

        // Sort the scores in descending order
        scores.sortByDescending { it.score }

        // Limit the leaderboard to top 3 scores
        if (scores.size > 3) {
            scores.removeLast()
        }
    }

    fun getTopScores(): List<Player> {
        return scores
    }


    data class Score(val value: Int)

}
