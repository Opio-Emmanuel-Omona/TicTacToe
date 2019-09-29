package com.example.tictactoe

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var MY_TEXT: String
    private lateinit var drawer: DrawXO
    private lateinit var aiPlay: AI_Play
    private lateinit var context: Context
    private var myTurn: Boolean = false

    private lateinit var updateUIAsync: UpdateUIAsync

    lateinit var buttonList: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialiseObjects()
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

    }

    /**
     * Initialises all objects
     */
    private fun initialiseObjects() {
        instance = this
        context = applicationContext

        drawer = DrawXO(context)

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
            myTurn = true
            Toast.makeText(applicationContext, "You Start", Toast.LENGTH_SHORT).show()
        } else {
            my_letter_view.text = "O"
            ai_letter_view.text = "X"
            aiPlay = AI_Play(context, "X")
            myTurn = false
            Toast.makeText(applicationContext, "AI Starts", Toast.LENGTH_LONG).show()
            ai_play()

        }
    }

    /**
     * Called from each button click listener and executes an async task to update the button views
     * while the computer is calculating it's move.
     * This is only executed if it is the players turn
     *
     * @param button specifies the button that has been clicked
     */
    private fun buttonClicked(button: Button) {
        if (isMyTurn()) {
            updateUIAsync = UpdateUIAsync()
            updateUIAsync.execute(button)
        }
    }

    /**
     * Getter for myTurn
     */
    fun isMyTurn(): Boolean{
        return myTurn
    }

    /**
     * Setter for myTurn
     */
    fun setMyTurn(value: Boolean) {
        myTurn = value
    }

    /**
     * Function that starts the ai's move
     */
    fun ai_play() {
        aiPlay.play()
    }

    /**
     * function that loops through all the buttons in search for those that have not yet been played
     *
     * @return a list of button moves remaining
     */
    fun getEmptyButtons(): List<Button> {
        var emptyButtonList: MutableList<Button> = mutableListOf()
        for (button in buttonList) {
            if (button.isEnabled) {
                emptyButtonList.add(button)
            }
        }
        return emptyButtonList
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
            drawer.drawLabel(params[0]!!, MY_TEXT, isMyTurn())
            return params[0]
        }

        override fun onPostExecute(result: Button?) {
            super.onPostExecute(result)
            Log.d("Dubug result", "$result")
            result!!.isEnabled = false
            aiPlay.play()
        }

    }
}
