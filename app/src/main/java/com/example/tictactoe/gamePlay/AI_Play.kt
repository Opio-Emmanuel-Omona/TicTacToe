package com.example.tictactoe.gamePlay

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.tictactoe.gameLogic.AI_Logic

class AI_Play(private val context: Context, val AI_TEXT: String) {

    private val drawer = DrawXO(context)
    private val instance = MainActivity.getInstance()
    private lateinit var progressDialogAsync: ProgressDialogAsync
    private lateinit var ai_instance: AI_Logic

    fun play() {
        ai_instance = AI_Logic(AI_TEXT, instance.MY_TEXT)
        progressDialogAsync = ProgressDialogAsync(instance)
        progressDialogAsync.execute()
    }



    inner class ProgressDialogAsync(activity: MainActivity) : AsyncTask<Void, Void, Void>() {
        private var progressDialog: ProgressDialog = ProgressDialog(activity)
        private lateinit var aiMove: AI_Logic.Moves

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog.setMessage("AI thinking ...")
            progressDialog.show()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            aiMove = ai_instance.minimax(instance.game.gameState, AI_TEXT)

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val aiButton = instance.buttonList[(aiMove.indx).toInt()]
            instance.game.winList = arrayListOf()

            val index = aiButton.toString().substringAfterLast("button_").substringBefore("}")
            MainActivity.getInstance().disableView(mutableListOf(aiButton))
            drawer.drawLabel(aiButton, index.toInt(),  AI_TEXT)

            if (ai_instance.checkAiWin(AI_TEXT, instance.game)) {
                Toast.makeText(context, "AI Wins", Toast.LENGTH_SHORT).show()
                instance.drawWin(instance.game.winList, AI_TEXT)
                instance.score.aiScore++
                instance.score.updateScore()
            } else if (ai_instance.checkAiDraw(instance.game)) {
                Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show()
            }

            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        }
    }
}
