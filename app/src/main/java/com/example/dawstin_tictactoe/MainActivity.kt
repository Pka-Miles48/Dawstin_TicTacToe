package com.example.dawstin_tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dawstin_tictactoeinterface.R

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private var playerTurn = true // true for X, false for O
    private var gameActive = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcomeTextView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttons = arrayOf(
            arrayOf(findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3)),
            arrayOf(findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6)),
            arrayOf(findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9))
        )

        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].setOnClickListener { onButtonClick(buttons[i][j]) }
            }
        }

        findViewById<Button>(R.id.newGameButton).setOnClickListener { newGame() }
    }

    private fun onButtonClick(button: Button) {
        if (!gameActive || button.text.isNotEmpty()) return

        button.text = if (playerTurn) "X" else "O"
        if (checkForWin()) {
            findViewById<TextView>(R.id.gameResultTextView).text = if (playerTurn) "Player X wins!" else "Player O wins!"
            gameActive = false
        } else if (isBoardFull()) {
            findViewById<TextView>(R.id.gameResultTextView).text = "It's a tie!"
            gameActive = false
        } else {
            playerTurn = !playerTurn
            findViewById<TextView>(R.id.playerTurnTextView).text = if (playerTurn) "Player X's Turn" else "Player O's Turn"
        }
    }

    private fun checkForWin(): Boolean {
        // Check rows, columns, and diagonals for a win
        for (i in 0..2) {
            if (buttons[i][0].text == buttons[i][1].text && buttons[i][1].text == buttons[i][2].text && buttons[i][0].text.isNotEmpty()) return true
            if (buttons[0][i].text == buttons[1][i].text && buttons[1][i].text == buttons[2][i].text && buttons[0][i].text.isNotEmpty()) return true
        }
        if (buttons[0][0].text == buttons[1][1].text && buttons[1][1].text == buttons[2][2].text && buttons[0][0].text.isNotEmpty()) return true
        if (buttons[0][2].text == buttons[1][1].text && buttons[1][1].text == buttons[2][0].text && buttons[0][2].text.isNotEmpty()) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                if (buttons[i][j].text.isEmpty()) return false
            }
        }
        return true
    }

    private fun newGame() {
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].text = ""
            }
        }
        gameActive = true
        playerTurn = true
        findViewById<TextView>(R.id.playerTurnTextView).text = "Player X's Turn"
        findViewById<TextView>(R.id.gameResultTextView).text = ""
    }
}
