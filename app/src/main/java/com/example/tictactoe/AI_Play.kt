package com.example.tictactoe

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Button
import android.widget.Toast

class AI_Play(private val context: Context, val AI_TEXT: String) {

    private val drawer = DrawXO(context)
    private val instance = MainActivity.getInstance()
    private lateinit var progressDialogAsync: ProgressDialogAsync

    fun play() {
        progressDialogAsync = ProgressDialogAsync(instance)
        progressDialogAsync.execute()
    }

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

        var moves = mutableListOf<Moves>()

        for (i in availMoves) {
            val move = Moves(0, "")
            move.indx = gameState[i]

            gameState[i] = label

            if (label == AI_TEXT){
                val result = minimax(gameState, instance.MY_TEXT)
                move.score = result.score
            }
            else{
                val result = minimax(gameState, AI_TEXT)
                move.score = result.score
            }

            gameState[i] = move.indx

            moves.add(move)
        }

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

    private fun emptySpots(gameState: MutableList<String>): MutableList<Int> {
        val emptySpotList = mutableListOf<Int>()
        for (i in gameState) {
            if (i != AI_TEXT && i != instance.MY_TEXT) {
                emptySpotList.add(gameState.indexOf(i))
            }
        }
        return emptySpotList
    }

    inner class Moves(var score: Int, var indx: String)

    inner class ProgressDialogAsync(activity: MainActivity) : AsyncTask<Void, Void, Void>() {
        private var progressDialog: ProgressDialog = ProgressDialog(activity)
        private lateinit var aiMove: Moves

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog.setMessage("AI thinking ...")
            progressDialog.show()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            aiMove = minimax(instance.game.gameState, AI_TEXT)

            return null
        }

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