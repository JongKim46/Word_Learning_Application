package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.Http.word_learning_application.HttpSpringHelp
import com.Http.word_learning_application.SelectWordListAPI
import com.Http.word_learning_application.WordAPI
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_second_choose.*
import org.json.JSONObject



class SecondChooseActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_choose)

        //AD
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        /*学習Level*/
        val wordLevle = intent.getStringExtra("wordLever")
        /*タイトル設定*/
        wordTitle.text = wordLevle + "単語学習時間選択"

        val wordLearning = Intent(this, WordLearning::class.java)
        /*選択した学習レベル取得*/
        wordLearning.putExtra("wordLever", wordLevle)
        /*何番目の学習画面なのか取得*/
        wordLearning.putExtra("screenCount", 0)

        /*DB成城*/
        val wordLists = SelectWordListAPI().wordSelectThread(wordLevle!!)

        //3秒選択
        time_3.setOnClickListener {
            Log.d("wordSelectList", wordLists.toString())
            wordLearning.putExtra("chooseTime", 3)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }
        //5秒選択
        time_5.setOnClickListener {
            //wordLists = dbHelper.select(database, wordLevle!!, wordLists)!!
            wordLearning.putExtra("chooseTime", 5)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }
        //10秒選択
        time_10.setOnClickListener {
            //wordLists = dbHelper.select(database, wordLevle!!, wordLists)!!
            wordLearning.putExtra("chooseTime", 10)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }

        cancel_button.setOnClickListener {
            val wordLearning = Intent(this, MainActivity::class.java)
            wordLearning.putExtra("DBcheck", false)
            startActivity(wordLearning)
            finish()
        }

    }
}