package com.example.tictactoe.gameLogic

class AI_Logic(var aiLabel: String, var humanLabel: String) {


    fun minimax(gameState: MutableList<String>, currentLabel: String): Moves {
        var availMoves = emptySpots(gameState)
        val game = Game(gameState)

        when {
            game.checkWin(aiLabel) -> {
                return Moves(1, aiLabel)
            }
            game.checkWin(humanLabel) -> {
                return Moves(-1, humanLabel)
            }
            availMoves.isEmpty() -> {
                return Moves(0, "")
            }
        }

        var moves = mutableListOf<Moves>()

        for (i in availMoves) {
            val move = Moves(0, "")
            move.indx = gameState[i]

            gameState[i] = currentLabel

            if (currentLabel == aiLabel){
                val result = minimax(gameState, humanLabel)
                move.score = result.score
            }
            else{
                val result = minimax(gameState, aiLabel)
                move.score = result.score
            }

            gameState[i] = move.indx

            moves.add(move)
        }

        var bestMove = 0
        if(currentLabel == aiLabel){
            var bestScore = -10000
            for (i in moves) {
                if (i.score > bestScore) {
                    bestScore = i.score
                    bestMove = moves.indexOf(i)
                }
            }
        } else {
            var bestScore = 10000
            for (i in moves) {
                if (i.score < bestScore) {
                    bestScore = i.score
                    bestMove = moves.indexOf(i)
                }
            }
        }

        return moves[bestMove]
    }

    fun checkAiWin(label: String, gameInstance: Game): Boolean {
        return gameInstance.checkWin(label)
    }

    fun checkAiDraw(gameInstance: Game): Boolean {
        return gameInstance.checkDraw()
    }

    private fun emptySpots(gameState: MutableList<String>): MutableList<Int> {
        val emptySpotList = mutableListOf<Int>()
        for (i in gameState) {
            if (i != "X" && i != "O") {
                emptySpotList.add(gameState.indexOf(i))
            }
        }
        return emptySpotList
    }

    inner class Moves(var score: Int, var indx: String)

}
