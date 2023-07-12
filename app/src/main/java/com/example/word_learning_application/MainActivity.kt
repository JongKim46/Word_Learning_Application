package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val secondScreen = Intent(this, SecondChooseActivity::class.java)

        N5_button.setOnClickListener {
            val wordLever = "N5"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N4_button.setOnClickListener {
            val wordLever = "N4"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N3_button.setOnClickListener {
            val wordLever = "N3"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N2_button.setOnClickListener {
            val wordLever = "N2"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N1_button.setOnClickListener {
            val wordLever = "N1"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
    }
}