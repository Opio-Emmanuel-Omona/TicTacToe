package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var MY_TEXT: String
    private lateinit var draw: DrawXO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        draw = DrawXO(applicationContext)

        chooseWhoStarts()

        button_0.setOnClickListener {
            draw.drawLabel(button_0, MY_TEXT)
        }

        button_1.setOnClickListener {
            draw.drawLabel(button_1, MY_TEXT)
        }

        button_2.setOnClickListener {
            draw.drawLabel(button_2, MY_TEXT)
        }

        button_3.setOnClickListener {
            draw.drawLabel(button_3, MY_TEXT)
        }

        button_4.setOnClickListener {
            draw.drawLabel(button_4, MY_TEXT)
        }

        button_5.setOnClickListener {
            draw.drawLabel(button_5, MY_TEXT)
        }

        button_6.setOnClickListener {
            draw.drawLabel(button_6, MY_TEXT)
        }

        button_7.setOnClickListener {
            draw.drawLabel(button_7, MY_TEXT)
        }

        button_8.setOnClickListener {
            draw.drawLabel(button_8, MY_TEXT)
        }

    }

    private fun chooseWhoStarts() {
        MY_TEXT = draw.getMyLetter()
        setLetters()
    }

    private fun setLetters() {
        if (MY_TEXT == "X") {
            my_letter.text = "X"
            ai_letter.text = "O"
            Toast.makeText(applicationContext, "You Start", Toast.LENGTH_SHORT).show()
        } else {
            my_letter.text = "O"
            ai_letter.text = "X"
            Toast.makeText(applicationContext, "AI Starts", Toast.LENGTH_SHORT).show()
        }
    }
}
