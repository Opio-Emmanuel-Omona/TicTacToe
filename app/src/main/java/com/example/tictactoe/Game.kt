package com.example.tictactoe

class Game(var gameState: MutableList<String>) {

    var winList = arrayListOf<Int>()
    var moveCount = 0

    fun checkWin(letter: String): Boolean {
        if (anyIsTrue(checkCol(letter), checkRow(letter), checkDiagL(letter), checkDiagR(letter))) {
            return true
        }
        return false
    }

    fun checkDraw(): Boolean {
        return moveCount == 9
    }

    private fun checkCol(letter: String): Boolean {
        for (i in 0 until 3) {
            if (areAllEqual(gameState[i], gameState[i + 3], gameState[i + 6], letter)) {
                addWinList(i, i + 3, i + 6)
                return true
            }
        }
        return false
    }

    private fun checkRow(letter: String): Boolean {
        for (i in 0 until 8 step 3) {
            if (areAllEqual(gameState[i], gameState[i + 1], gameState[i + 2], letter)) {
                addWinList(i, i + 1, i + 2)
                return true
            }
        }
        return false
    }

    private fun checkDiagR(letter: String): Boolean {
        if (areAllEqual(gameState[0], gameState[4], gameState[8], letter)) {
            addWinList(0, 4, 8)
            return true
        }
        return false
    }

    private fun  checkDiagL(letter: String): Boolean {
        if (areAllEqual(gameState[2], gameState[4], gameState[6], letter)) {
            addWinList(2, 4, 6)
            return true
        }
        return false
    }

    private fun addWinList(vararg ints: Int) {
        for (i in ints) {
            winList.add(i)
        }
    }

    private fun areAllEqual(vararg values: String): Boolean {
        val checkValue = values[0]
        for (i in 1 until values.size) {
            if (values[i] != checkValue) {
                return false
            }
        }
        return true
    }

    private fun anyIsTrue(vararg values: Boolean): Boolean {
        for (i in values) {
            if (i) return true
        }
        return false
    }
}
