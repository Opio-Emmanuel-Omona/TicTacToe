package com.example.tictactoe

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button

class DrawXO(context: Context) {

    private val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
    private val instance = MainActivity.getInstance()

    fun drawLabel(button: Button, index: Int, label: String) {
        button.text = label

        if (!Turns.myTurn) {
            button.startAnimation(shake)
        }

        instance.game.gameState[index] = label
        instance.game.moveCount++
        Turns.switchTurns()
    }
}
