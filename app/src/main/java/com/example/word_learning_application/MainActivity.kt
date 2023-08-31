package com.example.word_learning_application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.N1_button
import kotlinx.android.synthetic.main.activity_main.N2_button
import kotlinx.android.synthetic.main.activity_main.N3_button
import kotlinx.android.synthetic.main.activity_main.N4_button
import kotlinx.android.synthetic.main.activity_main.N5_button


import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val secondScreen = Intent(this, SecondChooseActivity::class.java)

        MobileAds.initialize(this) {}


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

