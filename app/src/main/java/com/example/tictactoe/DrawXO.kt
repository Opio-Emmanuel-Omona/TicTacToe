package com.example.tictactoe

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button

/**
 * class that draws the letters on the buttons
 * and also handles the animation
 */
class DrawXO(context: Context) {

    private val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)

    /**
     * function that draws the text
     * also animates move only when AI plays.
     *
     * @param button the button to draw the letter on
     * @param label the letter to be drawn on the button
     * @param myTurn to indicate the player making the move true for player false for AI
     */
    fun drawLabel(button: Button, label: String) {
        button.text = label

        if (!Turns.myTurn) {
            button.startAnimation(shake)
        }
        Turns.switchTurns()
    }
}
