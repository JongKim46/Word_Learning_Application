package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second_choose.*

class SecondChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_choose)
        //学習LEVEL
        val wordLevle = intent.getStringExtra("wordLever")
        wordTitle.text = wordLevle + "単語学習時間選択"

        val wordLearning = Intent(this, WordLearning::class.java)
        wordLearning.putExtra("wordLever", wordLevle)
        wordLearning.putExtra("screenCount", "1")
        //3秒選択
        time_3.setOnClickListener {
            wordLearning.putExtra("chooseTime", "3")
            startActivity(wordLearning)
        }
        //5秒選択
        time_5.setOnClickListener {
            wordLearning.putExtra("chooseTime", "5")
            startActivity(wordLearning)
        }
        //10秒選択
        time_10.setOnClickListener {
            wordLearning.putExtra("chooseTime", "10")
            startActivity(wordLearning)
        }

        cancel_button.setOnClickListener {
            finish()
        }
    }
}