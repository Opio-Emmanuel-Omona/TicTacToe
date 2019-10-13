package com.example.tictactoe

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Score(private var context: Context) {

    private val SCOREPREF = "SCORE PREF"
    private var scorePref: SharedPreferences

    var myScore: Int = 0
    var aiScore: Int = 0

    init {
        scorePref = context.getSharedPreferences(SCOREPREF, MODE_PRIVATE)
    }

    fun updateScore() {
        val scorePrefEditor = context.getSharedPreferences(SCOREPREF, MODE_PRIVATE).edit()
        scorePrefEditor.putInt("myScore", myScore)
        scorePrefEditor.putInt("aiScore", aiScore)
        scorePrefEditor.apply()
        MainActivity.getInstance().updateScore()
    }

    fun getScore(): List<Int> {
        scorePref = context.getSharedPreferences(SCOREPREF, MODE_PRIVATE)
        myScore = scorePref.getInt("myScore", 0)
        aiScore = scorePref.getInt("aiScore", 0)
        return listOf(myScore, aiScore)
    }
}
