package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_test_results.*

class TestResults : AppCompatActivity() {
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)

        //AD
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        var wordLevle = intent.getStringExtra("wordLever")
        var wordLists = intent?.getSerializableExtra("wordLists") as ArrayList<WordResult>

        /*学習言語*/
        val wordLanguage = intent.getStringExtra("language")

        wordTitle.text = "$wordLevle TEST結果"
            var wordList = WordResultList(this, wordLists, wordLanguage)
            wordTestResultList.adapter = wordList


        Home.setOnClickListener {
            val wordLearning = Intent(this, MainActivity::class.java)
            wordLearning.putExtra("DBcheck", false)
            wordLearning.putExtra("language", wordLanguage)
            startActivity(wordLearning)
            finish()
        }
    }
}