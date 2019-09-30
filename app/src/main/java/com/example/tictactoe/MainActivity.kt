package com.example.tictactoe

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var MY_TEXT: String
    private lateinit var drawer: DrawXO
    private lateinit var aiPlay: AI_Play
    private lateinit var context: Context

    private lateinit var updateUIAsync: UpdateUIAsync
    private lateinit var dismissDialogAsync: DismissDialogAsync

    lateinit var buttonList: List<Button>
    lateinit var game: Game
    lateinit var score: Score

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialiseObjects()
        updateScore()
        chooseWhoStarts()

        button_0.setOnClickListener {
            buttonClicked(button_0)
        }

        button_1.setOnClickListener {
            buttonClicked(button_1)
        }

        button_2.setOnClickListener {
            buttonClicked(button_2)
        }

        button_3.setOnClickListener {
            buttonClicked(button_3)
        }

        button_4.setOnClickListener {
            buttonClicked(button_4)

        }

        button_5.setOnClickListener {
            buttonClicked(button_5)
        }

        button_6.setOnClickListener {
            buttonClicked(button_6)
        }

        button_7.setOnClickListener {
            buttonClicked(button_7)
        }

        button_8.setOnClickListener {
            buttonClicked(button_8)
        }

        reset.setOnClickListener {
            reset()
        }

    }

    private fun displayDialog() {
        val message =
            if (Turns.myTurn) "You Start"
            else "AI Starts"
        dismissDialogAsync = DismissDialogAsync()
        AlertDialog.Builder(this)
            .setTitle(message)
            .setPositiveButton(android.R.string.ok) { _, _ -> dismissDialogAsync.execute() }
            .show()
    }

    /**
     * update the UI with the latest score from shared preferences
     */
    fun updateScore() {
        val scoreList = score.getScore()
        my_score.text = scoreList[0].toString()
        ai_score.text = scoreList[1].toString()
    }

    /**
     * Initialises all objects and views
     */
    private fun initialiseObjects() {
        instance = this
        context = applicationContext

        drawer = DrawXO(context)
        score = Score(context)

        // initialise the game state to empty
        val list = mutableListOf("0", "1", "2", "3", "4", "5", "6", "7", "8")
        game = Game(list)

        buttonList = arrayListOf(
            button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8
        )
    }

    /**
     * Function that determines who starts by randomly selecting a letter for player either X or O
     */
    private fun chooseWhoStarts() {
        val source = "XO"
        MY_TEXT = source.random().toString()
        setLetters()
    }

    /**
     * Function that sets the views indicating which player uses which letter
     * X always starts
     * if AI is playing X then AI starts otherwise player starts
     */
    private fun setLetters() {
        if (MY_TEXT == "X") {
            my_letter_view.text = "X"
            ai_letter_view.text = "O"
            aiPlay = AI_Play(context, "O")
            Turns.myTurn = true
        } else {
            my_letter_view.text = "O"
            ai_letter_view.text = "X"
            aiPlay = AI_Play(context, "X")
            Turns.myTurn = false
        }
        displayDialog()
    }

    /**
     * Called from each button click listener and executes an async task to update the button views
     * while the computer is calculating it's move.
     * This is only executed if it is the players turn
     *
     * @param button specifies the button that has been clicked
     */
    private fun buttonClicked(button: Button) {
        if (Turns.myTurn) {
            updateUIAsync = UpdateUIAsync()
            updateUIAsync.execute(button)
        }
    }

    /**
     * Function that starts the ai's move
     */
    fun ai_play() {
        aiPlay.play()
    }

    /**
     * Helper function to disable the buttons that have been clicked and also when the game ends
     *
     * @param views list of buttons to disable
     */
    fun disableView(views: MutableList<Button>) {
        for (i in views) {
            i.isEnabled = false
        }
    }

    /**
     * helper function that enables a view for a new game
     *
     * @param views list of buttons to enable
     */
    private fun enableView(views: List<Button>) {
        for (i in views) {
            i.isEnabled = true
            i.setTextColor(resources.getColor(R.color.white))
            i.text = ""
        }
    }

    /**
     * to start a new game
     */
    fun reset() {
        enableView(buttonList)

        // new game state
        val list = mutableListOf("0", "1", "2", "3", "4", "5", "6", "7", "8")
        game = Game(list)

        chooseWhoStarts()
    }

    /**
     * Helper function to color the winning labels on the board
     *
     * @param array the list of buttons to color
     * @param letter the label to draw on the buttons
     */
    fun drawWin(array: ArrayList<Int>, letter: String) {
        for (i in array) {
            buttonList[i].setTextColor(resources.getColor(R.color.yellow))
            buttonList[i].text = letter
        }
    }

    /**
     * companion object containing static references
     */
    companion object {
        private lateinit var instance: MainActivity

        /**
         * called to access the members of this activity e.g MainActivity.getInstance().anyMember()
         *
         * @return an instance of this activity
         */
        fun getInstance(): MainActivity {
            return instance
        }
    }

    /**
     * An inner class that implements the AsyncTask class to update the UI with the player's move
     * while the AI ponders a move.
     */
    inner class UpdateUIAsync : AsyncTask<Button, Void, Button> () {
        override fun doInBackground(vararg params: Button?): Button? {
            val index = params[0].toString().substringAfterLast("button_").substringBefore("}")
            drawer.drawLabel(params[0]!!, index.toInt(), MY_TEXT)
            return params[0]
        }

        override fun onPostExecute(result: Button?) {
            super.onPostExecute(result)
            disableView(mutableListOf(result!!))

            //check win
            if (instance.game.checkWin(MY_TEXT)){
                drawWin(game.winList, MY_TEXT)
                Toast.makeText(context, "You Win", Toast.LENGTH_SHORT).show()
                instance.disableView(instance.buttonList as MutableList<Button>)
                score.myScore++
                score.updateScore()
            } else if (instance.game.checkDraw()) {
                Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show()
            } else {
                ai_play()
            }
        }
    }

    inner class DismissDialogAsync: AsyncTask<Void, Void, Boolean> () {
        override fun doInBackground(vararg params: Void?): Boolean {
            return Turns.myTurn
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)

            if (!result!!) {
                ai_play()
            }
        }
    }
}
