package com.example.tictactoe

import android.content.Context
import android.widget.Button
import android.widget.Toast

/**
 * class containing AI's logic for moves
 */
class AI_Play(private val context: Context, val AI_TEXT: String) {

    private val drawer = DrawXO(context)
    private val instance = MainActivity.getInstance()

    /**
     * method that makes the move for the AI
     * waits for a second before playing
     * then disables the button after
     */
    fun play() {
        Thread.sleep(1000)
        val emptyButtonList = MainActivity.getInstance().getEmptyButtons()
        val aiButton = emptyButtonList.random()
        val index = aiButton.toString().substringAfterLast("button_").substringBefore("}")
        MainActivity.getInstance().disableView(mutableListOf(aiButton))
        drawer.drawLabel(aiButton, index.toInt(),  AI_TEXT)

        checkAiWin()
    }

    /**
     * funcgion to check whether the Ai has won
     * it is called every after the ai makes a move
     */
    private fun checkAiWin() {
        if (instance.game.checkWin(AI_TEXT)){
            Toast.makeText(context, "AI wins", Toast.LENGTH_SHORT).show()
            instance.disableView(instance.buttonList as MutableList<Button>)
            instance.score.aiScore++
            instance.score.updateScore()
        } else if (instance.game.checkDraw()) {
            Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show()
        }
    }
}