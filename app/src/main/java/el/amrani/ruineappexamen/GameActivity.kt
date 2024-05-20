import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import el.amrani.ruineappexamen.ui.theme.RuineAPPEXAMENTheme
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RuineAPPEXAMENTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    GameScreen(onGameEnd = { finish() })
                }
            }
        }
    }
}

@Composable
fun GameScreen(onGameEnd: () -> Unit) {
    var playerFortune by remember { mutableStateOf(Random.nextInt(1, 101)) }
    var casinoFortune by remember { mutableStateOf(Random.nextInt(10, 101)) }
    var message by remember { mutableStateOf("") }
    var gameOver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Player's Fortune: $playerFortune €",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Casino's Fortune: $casinoFortune €",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Message: $message",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (!gameOver) {
            Button(
                onClick = {
                    val diceRoll = Random.nextInt(1, 7)
                    if (diceRoll == 2 || diceRoll == 3) {
                        playerFortune++
                        casinoFortune--
                        message = "Player won this round!"
                    } else {
                        playerFortune--
                        casinoFortune++
                        message = "Casino won this round!"
                    }

                    if (playerFortune <= 0 || casinoFortune <= 0) {
                        gameOver = true
                        if (playerFortune <= 0 && casinoFortune <= 0) {
                            message = "It's a tie!"
                        } else if (playerFortune <= 0) {
                            message = "Casino wins!"
                        } else {
                            message = "Player wins!"
                        }
                        onGameEnd() // Call the callback to end the game
                    }
                }
            ) {
                Text("Roll Dice")
            }
        } else {
            Button(
                onClick = { onGameEnd() }
            ) {
                Text("End Game")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    RuineAPPEXAMENTheme {
        GameScreen(onGameEnd = {})
    }
}

