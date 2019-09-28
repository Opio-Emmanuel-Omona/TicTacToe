package com.example.tictactoe

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button

class DrawXO(context: Context) {

    private val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)

    fun drawLabel(button: Button, label: String) {
        button.text = label
        button.startAnimation(shake)
        button.isEnabled = false
    }

    fun getMyLetter(): String {
        val source = "XO"
        return source.random().toString()
    }
}
