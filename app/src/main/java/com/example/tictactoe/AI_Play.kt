package com.example.tictactoe

import android.content.Context

/**
 * class containing AI's logic for moves
 */
class AI_Play(context: Context, val AI_TEXT: String) {

    val drawer = DrawXO(context)

    /**
     * method that makes the move for the AI
     * waits for a second before playing
     * then disables the button after
     */
    fun play() {
        Thread.sleep(1000)
        val emptyButtonList = MainActivity.getInstance().getEmptyButtons()
        val aiButton = emptyButtonList.random()
        MainActivity.getInstance().setMyTurn(false)
        drawer.drawLabel(aiButton, AI_TEXT, MainActivity.getInstance().isMyTurn())
        aiButton.isEnabled = false
    }
}