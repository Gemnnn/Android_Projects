package com.example.minigames

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.minigames.viewmodel.MemoryGameViewModel

class MemoryGameFragment : Fragment() {

    private lateinit var gameGrid: GridLayout
    private lateinit var scoreTextView: TextView
    private lateinit var roundTextView: TextView
    private lateinit var tilesToMemorizeTextView: TextView
    private val viewModel: MemoryGameViewModel by activityViewModels()

    private val selectedTiles = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memory_game, container, false)

        gameGrid = view.findViewById(R.id.gameGrid)
        scoreTextView = view.findViewById(R.id.scoreTextView)
        roundTextView = view.findViewById(R.id.roundTextView)
        tilesToMemorizeTextView = view.findViewById(R.id.tilesToMemorizeTextView)

        setupGameGrid()
        observeViewModel()

        val playerName = arguments?.getString("playerName") ?: "Unknown"
        val gameMode = arguments?.getString("gameMode") ?: "Normal"
        viewModel.startGame(playerName, gameMode)

        return view
    }

    private fun setupGameGrid() {
        gameGrid.removeAllViews()
        val tileSize = resources.getDimensionPixelSize(R.dimen.tile_size)

        for (i in 0 until 36) {
            val button = Button(requireContext()).apply {
                id = View.generateViewId()
                layoutParams = GridLayout.LayoutParams().apply {
                    width = tileSize
                    height = tileSize
                    setMargins(5, 5, 5, 5)
                }
                setOnClickListener { onTileSelected(this.id) }
            }
            gameGrid.addView(button)
        }
    }

    private fun onTileSelected(tileId: Int) {
        if (!viewModel.isUserTurn || selectedTiles.size >= viewModel.getCurrentTilesToMemorize()) return

        selectedTiles.add(tileId)
        if (selectedTiles.size == viewModel.getCurrentTilesToMemorize()) {
            viewModel.onTileSelected(selectedTiles)
            selectedTiles.clear()
        }
    }

    private fun observeViewModel() {
        viewModel.highlightedTiles.observe(viewLifecycleOwner) { tiles ->
            highlightTiles(tiles)
            Handler(Looper.getMainLooper()).postDelayed({
                clearHighlightedTiles()
            }, viewModel.getHighlightDuration())
        }

        viewModel.gameOver.observe(viewLifecycleOwner) { isGameOver ->
            if (!isGameOver) {
                Toast.makeText(context, "Game Over!", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
            }

        }

        viewModel.player.observe(viewLifecycleOwner) { player ->
            scoreTextView.text = "Score: ${player.score}"
            roundTextView.text = "Round: ${viewModel.getCurrentRound()}"
            tilesToMemorizeTextView.text = "Tiles: ${viewModel.getCurrentTilesToMemorize()}"
        }
    }

    private fun highlightTiles(tiles: List<Int>) {
        for (tileId in tiles) {
            gameGrid.findViewById<Button>(tileId)?.setBackgroundResource(R.color.yellow)
        }
    }

    private fun clearHighlightedTiles() {
        for (i in 0 until gameGrid.childCount) {
            gameGrid.getChildAt(i)?.setBackgroundResource(R.color.white)
        }
    }
}
