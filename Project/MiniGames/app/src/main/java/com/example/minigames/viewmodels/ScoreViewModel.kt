import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minigames.models.Player
import com.example.minigames.models.Score


class ScoreViewModel : ViewModel() {
    private val _topPlayers = MutableLiveData<List<Player>>()
    val topPlayers: LiveData<List<Player>> = _topPlayers

    private val _topScores = MutableLiveData<List<Player>>()
    val topScores: LiveData<List<Player>> = _topScores

    init {
        fetchTopPlayers()
    }

    private fun fetchTopPlayers() {

        val players = getTopPlayers()
        _topPlayers.value = players
    }

    private fun getTopPlayers(): List<Player> {
        return listOf(
            Player("Alice", 120),
            Player("Bob", 110),
            Player("Charlie", 100)
        )
    }
}
