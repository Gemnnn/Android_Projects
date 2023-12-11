package com.example.minigames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.minigames.viewmodel.MemoryGameViewModel
import com.example.minigames.viewmodel.SetupViewModel

class GameFragment : Fragment() {

    private val setupViewModel: SetupViewModel by activityViewModels()

    private val memoryGameViewModel: MemoryGameViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val startButton = view.findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            if (validateInput()) {
                navigateToMemoryGame()
            }
        }

        return view
    }

    private fun validateInput(): Boolean {
        val playerNameEditText = view?.findViewById<EditText>(R.id.playerNameEditText)
        val playerName = playerNameEditText?.text.toString().trim()

        if (playerName.isBlank()) {
            Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
            return false
        }

        val radioGroup = view?.findViewById<RadioGroup>(R.id.radioGroup)
        val selectedModeId = radioGroup?.checkedRadioButtonId ?: -1
        if (selectedModeId == -1) {
            Toast.makeText(context, "Please select a game mode", Toast.LENGTH_SHORT).show()
            return false
        }

        setupViewModel.setPlayerName(playerName)
        setupViewModel.setGameDifficulty(getSelectedDifficulty(selectedModeId))

        return true
    }

    private fun getSelectedDifficulty(selectedModeId: Int): String {
        return when (selectedModeId) {
            R.id.normalModeButton -> "Normal"
            R.id.hardModeButton -> "Hard"
            else -> "Normal" // Default to normal if somehow no button is selected
        }
    }

    private fun navigateToMemoryGame() {
        val memoryGameFragment = MemoryGameFragment()
        val bundle = Bundle()
        bundle.putString("playerName", setupViewModel.playerName.value)
        bundle.putString("gameMode", setupViewModel.gameDifficulty.value)
        memoryGameFragment.arguments = bundle

        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, memoryGameFragment)?.commit()
    }
}
