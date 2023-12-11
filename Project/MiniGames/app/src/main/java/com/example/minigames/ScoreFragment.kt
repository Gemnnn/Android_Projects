package com.example.minigames

import ScoreViewModel
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.minigames.databinding.FragmentScoreBinding
import com.example.minigames.models.Player
// Make sure to import ScoreViewModel correctly

class ScoreFragment : Fragment() {
    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScoreViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.topScores.observe(viewLifecycleOwner) { players ->
            updateUI(players)
        }
    }

    private fun updateUI(players: List<Player>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            players.map {
                // Adjust this line based on how score is represented in the Player class
                "${it.name}: ${it.score}"
            })
        binding.lvScores.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScoreFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
