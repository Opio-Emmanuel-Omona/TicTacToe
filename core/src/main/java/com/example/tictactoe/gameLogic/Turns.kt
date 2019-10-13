package com.example.tictactoe.gameLogic

class Turns {
    companion object {
        var myTurn: Boolean = false

        fun switchTurns() {
            myTurn = !myTurn
        }

        fun chooseWhoStarts() :String {
            val source = "XO"
            return source.random().toString()
        }
    }
}
