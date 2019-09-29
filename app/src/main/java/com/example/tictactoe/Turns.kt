package com.example.tictactoe

/**
 * class that handles the turns (who plays when)
 *
 */
class Turns {

    /**
     * static field myTurn is true for the player to move and false for the ai to move
     */
    companion object {
        var myTurn: Boolean = false

        /**
         * after any player has played, the turn is switched to the other player
         */
        fun switchTurns() {
            myTurn = !myTurn
        }
    }
}
