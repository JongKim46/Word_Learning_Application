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
        wordLearning.putExtra("screenCount", 0)
        var wordLists = arrayListOf<WordResult>()
        wordLists.add(WordResult(11,"1","1","あ","え",0))
        wordLists.add(WordResult(11,"2","2","か","け",0))
        wordLists.add(WordResult(11,"3","3","な","ね",0))
        wordLists.add(WordResult(11,"4","4","た","て",0))
        wordLists.add(WordResult(11,"5","5","ま","め",0))

        //3秒選択
        time_3.setOnClickListener {
            wordLists = wordLevle?.let { it1 -> selectWord(it1, wordLists) }!!
            wordLearning.putExtra("chooseTime", 3)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }
        //5秒選択
        time_5.setOnClickListener {
            wordLists = wordLevle?.let { it1 -> selectWord(it1,wordLists) }!!
            wordLearning.putExtra("chooseTime", 5)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }
        //10秒選択
        time_10.setOnClickListener {
            wordLists = wordLevle?.let { it1 -> selectWord(it1,wordLists) }!!
            wordLearning.putExtra("chooseTime", 10)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }

        cancel_button.setOnClickListener {
            finish()
        }

    }
    fun selectWord (wordLevel: String, wordLists: ArrayList<WordResult>): ArrayList<WordResult>{
        when(wordLevel){
            "N5" -> {
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
            }
            "N4" -> {
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
            }
            "N3" -> {
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
            }
            "N2" -> {
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
            }
            "N1" -> {
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
                wordLists.add(WordResult(11,"1","1","あ","え",0))
            }
        }
        return wordLists
    }


}