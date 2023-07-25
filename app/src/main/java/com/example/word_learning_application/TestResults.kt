package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test_results.*

class TestResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)

        var wordLevle = intent.getStringExtra("wordLever")
        var wordLists = intent?.getSerializableExtra("wordLists") as ArrayList<WordResult>


        wordTitle.text = "$wordLevle TEST結果"
            var wordList = WordResultList(this, wordLists)
            wordTestResultList.adapter = wordList


        Home.setOnClickListener {
            val wordLearning = Intent(this, MainActivity::class.java)
            wordLearning.putExtra("DBcheck", false)
            startActivity(wordLearning)
            finish()
        }
    }
}