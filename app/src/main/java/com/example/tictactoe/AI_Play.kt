package com.example.tictactoe

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Button
import android.widget.Toast

/**
 * class containing AI's logic for moves
 */
class AI_Play(private val context: Context, val AI_TEXT: String) {

    private val drawer = DrawXO(context)
    private val instance = MainActivity.getInstance()
    private lateinit var progressDialogAsync: ProgressDialogAsync

    /**
     * method that executes the aysnc task to start the ai move
     */
    fun play() {
        progressDialogAsync = ProgressDialogAsync(instance)
        progressDialogAsync.execute()
    }

    /**
     * function to check whether the Ai has won
     * it is called every after the ai makes a move
     */
    private fun checkAiWin() {
        if (instance.game.checkWin(AI_TEXT)){
            instance.drawWin(instance.game.winList, AI_TEXT)
            Toast.makeText(context, "AI wins", Toast.LENGTH_SHORT).show()
            instance.disableView(instance.buttonList as MutableList<Button>)
            instance.score.aiScore++
            instance.score.updateScore()
        } else if (instance.game.checkDraw()) {
            Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Minimax algorithm for AI move. (recursive)
     *
     * @param gameState the currentState of the game
     * @param label the letter to check with the algorithm
     */
    private fun minimax(gameState: MutableList<String>, label: String): Moves {
        var availMoves = emptySpots(gameState)

        when {
            instance.game.checkWin(AI_TEXT) -> {
                return Moves(1, AI_TEXT)
            }
            instance.game.checkWin(instance.MY_TEXT) -> {
                return Moves(-1, instance.MY_TEXT)
            }
            availMoves.isEmpty() -> {
                return Moves(0, "")
            }
        }

        // store for all the moves with terminal state
        var moves = mutableListOf<Moves>()

        // loop through available moves
        for (i in availMoves) {
            val move = Moves(0, "")
            move.indx = gameState[i]

            // set the empty spot to the current letter
            gameState[i] = label

            if (label == AI_TEXT){
                val result = minimax(gameState, instance.MY_TEXT)
                move.score = result.score
            }
            else{
                val result = minimax(gameState, AI_TEXT)
                move.score = result.score
            }

            //reset the gameState to the original state
            gameState[i] = move.indx

            // add the move to the moves collection
            moves.add(move)
        }

        // if it is the computer's turn loop over the moves
        // and choose the move with the highest score
        var bestMove = 0
        if(label == AI_TEXT){
            var bestScore = -10000
            for (i in moves) {
                if (i.score > bestScore) {
                    bestScore = i.score
                    bestMove = moves.indexOf(i)
                }
            }
        } else {
            // else loop over the moves and choose the move with the lowest score
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

    /**
     * Helper function to return all the Available moves
     */
    private fun emptySpots(gameState: MutableList<String>): MutableList<Int> {
        val emptySpotList = mutableListOf<Int>()
        for (i in gameState) {
            if (i != AI_TEXT && i != instance.MY_TEXT) {
                emptySpotList.add(gameState.indexOf(i))
            }
        }
        return emptySpotList
    }

    /**
     * Moves model class to provide the score and index
     */
    inner class Moves(var score: Int, var indx: String)

    /**
     * Async inner class to display progress dialog while AI computes a move
     */
    inner class ProgressDialogAsync(activity: MainActivity) : AsyncTask<Void, Void, Void>() {
        private var progressDialog: ProgressDialog = ProgressDialog(activity)
        private lateinit var aiMove: Moves

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog.setMessage("AI thinking ...")
            progressDialog.show()
        }

        /**
         * execute the minimax algorithm
         */
        override fun doInBackground(vararg params: Void?): Void? {
            aiMove = minimax(instance.game.gameState, AI_TEXT)

            return null
        }

        /**
         * dismiss the progress bar after computing the move
         */
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val aiButton = instance.buttonList[(aiMove.indx).toInt()]
            instance.game.winList = arrayListOf()

            val index = aiButton.toString().substringAfterLast("button_").substringBefore("}")
            MainActivity.getInstance().disableView(mutableListOf(aiButton))
            drawer.drawLabel(aiButton, index.toInt(),  AI_TEXT)

            checkAiWin()

            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        }

    }
}