package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_select_language.word_English
import kotlinx.android.synthetic.main.activity_select_language.word_Korean

class SelectLanguage : AppCompatActivity() {
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)

        val mainScreen = Intent(this, MainActivity::class.java)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        word_English.setOnClickListener {
            mainScreen.putExtra("language", "en")
            startActivity(mainScreen)
        }
        word_Korean.setOnClickListener {
            mainScreen.putExtra("language", "kr")
            startActivity(mainScreen)
        }

    }


}