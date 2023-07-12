package com.example.word_learning_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_word_learning.*
import kotlin.concurrent.thread

class WordLearning : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_learning)

        var wordLevle = intent.getStringExtra("wordLever")
        var chooseTime = intent?.getStringExtra("chooseTime")?.toLong()
        var screenCount = intent?.getStringExtra("screenCount")?.toInt()

        wordTitle.text = "$wordLevle 単語学習時間選択"
        wordCount.text = "$screenCount /10"

        thread (start = true){
            chooseTime?.let {
                Thread.sleep(chooseTime * 1000)
            }

            runOnUiThread{
                screenCount?.let {
                    screenCount++
                    wordCount.text = "$screenCount /10"
                }
            }
        }

        Home.setOnClickListener {
            finish()
        }
    }
}