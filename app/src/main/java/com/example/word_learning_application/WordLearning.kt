package com.example.word_learning_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_word_learning.*
import java.util.Timer
import kotlin.concurrent.timer

class WordLearning : AppCompatActivity() {
    private var vBinding : WordLearning? = null
    private val binding get() = vBinding!!
    var timer : Timer? = null
    var deltaTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_learning)

        var wordLevle = intent.getStringExtra("wordLever")
        var chooseTime = intent?.getStringExtra("chooseTime")?.toLong()
        var screenCount = intent?.getStringExtra("screenCount")?.toInt()

        wordTitle.text = "$wordLevle 単語学習時間選択"
        wordCount.text = "$screenCount /10"
        TimerFun()
/*
        Home.setOnClickListener {
            finish()
        }
   */
    }

    fun TimerFun() {
        // 0.1초에 1%씩 증가, 시작 버튼 누른 후 3초 뒤 시작
        timer = timer(period = 100, initialDelay = 3000) {
            if(deltaTime > 100) cancel()
            binding.progressBar1.setProgress(++deltaTime)
            println(binding.progressBar1.progress)
        }
    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }
}