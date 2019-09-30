package com.example.tictactoe

class Game(var gameState: MutableList<String>) {

    var winList = arrayListOf<Int>()
    var moveCount = 0

    /**
     * checks whether there are 3 connected letters in any direction
     *
     * @param letter the letter to check for the win
     */
    fun checkWin(letter: String): Boolean {
        if (anyIsTrue(checkCol(letter), checkRow(letter), checkDiagL(letter), checkDiagR(letter))) {
            return true
        }
        return false
    }

    /**
     * checks whether the board is filled up and therefore a draw
     */
    fun checkDraw(): Boolean {
        if (moveCount == 9) {
            return true
        }
        return false
    }

    /**
     * checks all columns for 3 connected letters
     *
     * @param letter the letter to check for the win
     */
    private fun checkCol(letter: String): Boolean {
        for (i in 0 until 3) {
            if (areAllEqual(gameState[i], gameState[i + 3], gameState[i + 6], letter)) {
                addWinList(i, i + 3, i + 6)
                return true
            }
        }
        return false
    }

    /**
     * checks all rows for 3 connected letters
     *
     * @param letter the letter to check for the win
     */
    private fun checkRow(letter: String): Boolean {
        for (i in 0 until 8 step 3) {
            if (areAllEqual(gameState[i], gameState[i + 1], gameState[i + 2], letter)) {
                addWinList(i, i + 1, i + 2)
                return true
            }
        }
        return false
    }

    /**
     * checks the diagonal from top left to bottom right for 3 connected letters
     *
     * @param letter the letter to check for the win
     */
    private fun checkDiagR(letter: String): Boolean {
        if (areAllEqual(gameState[0], gameState[4], gameState[8], letter)) {
            addWinList(0, 4, 8)
            return true
        }
        return false
    }

    /**
     * hecks the diagonal from top right to bottom left for 3 connected letters
     *
     * @param letter the letter to check for the win
     */
    private fun  checkDiagL(letter: String): Boolean {
        if (areAllEqual(gameState[2], gameState[4], gameState[6], letter)) {
            addWinList(2, 4, 6)
            return true
        }
        return false
    }

    /**
     * function that puts together all the connected indices for the win
     *
     * @param ints the 3 indices that make up the win
     */
    private fun addWinList(vararg ints: Int) {
        for (i in ints) {
            winList.add(i)
        }
    }

    /**
     * checks if all arguments passed are equal
     *
     * @param values the string values to check
     */
    private fun areAllEqual(vararg values: String): Boolean {
        val checkValue = values[0]
        for (i in 1 until values.size) {
            if (values[i] != checkValue) {
                return false
            }
        }
        return true
    }

    /**
     * checks if any argument passed is true
     *
     * @param values the values to check
     */
    private fun anyIsTrue(vararg values: Boolean): Boolean {
        for (i in values) {
            if (i) return true
        }
        return false
    }
}
