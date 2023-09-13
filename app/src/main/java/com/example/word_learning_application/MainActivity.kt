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
import kotlinx.android.synthetic.main.activity_main.cancel_button

class MainActivity : AppCompatActivity() {
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val secondScreen = Intent(this, SecondChooseActivity::class.java)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        /*学習言語*/
        val wordLanguage = intent.getStringExtra("language")
        secondScreen.putExtra("language", wordLanguage)

        N5_button.setOnClickListener {
            secondScreen.putExtra("wordLever", "N5")
            startActivity(secondScreen)
        }
        N4_button.setOnClickListener {
            secondScreen.putExtra("wordLever", "N4")
            startActivity(secondScreen)
        }
        N3_button.setOnClickListener {
            secondScreen.putExtra("wordLever", "N3")
            startActivity(secondScreen)
        }
        N2_button.setOnClickListener {
            secondScreen.putExtra("wordLever", "N2")
            startActivity(secondScreen)
        }
        N1_button.setOnClickListener {
            secondScreen.putExtra("wordLever", "N1")
            startActivity(secondScreen)
        }

        cancel_button.setOnClickListener {
            startActivity(Intent(this, SelectLanguage::class.java))
            finish()
        }
    }


}

