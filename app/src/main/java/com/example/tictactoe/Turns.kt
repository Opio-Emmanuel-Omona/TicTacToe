package com.example.tictactoe

class Turns {
    companion object {
        var myTurn: Boolean = false

        fun switchTurns() {
            myTurn = !myTurn
        }
    }
}
