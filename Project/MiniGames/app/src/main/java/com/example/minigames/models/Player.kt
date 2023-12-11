package com.example.minigames.models

data class Player(val name: String, var score: Int, var selectedTiles: MutableList<Int> = mutableListOf())


