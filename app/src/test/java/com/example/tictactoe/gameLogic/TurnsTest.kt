package com.example.tictactoe.gameLogic

import org.junit.Test
import org.junit.Assert.*

class TurnsTest {
    @Test
    fun switchTurns() {
        Turns.myTurn = false
        assertFalse(Turns.myTurn)
        Turns.switchTurns()
        assertTrue(Turns.myTurn)
    }

    @Test
    fun chooseWhoStartsReturnsCorrectLetter() {
        val letter = Turns.chooseWhoStarts()
        assertTrue(letterIsValid(letter))
    }

    fun letterIsValid(letter: String): Boolean {
        return (letter == "X" || letter == "O")
    }
}