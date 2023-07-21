package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlin.concurrent.thread
import kotlinx.android.synthetic.main.activity_word_learning.*


// UI処理をするためにHandlerクラス生成
val handler = Handler(Looper.getMainLooper())

class WordLearning : AppCompatActivity() {
    var mWorker: Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_learning)

        var wordLevle = intent?.getStringExtra("wordLever")
        var chooseTime = intent?.getIntExtra("chooseTime", 0)
        var screenCount: Int? = intent?.getIntExtra("screenCount", 0)?.toInt()
        var wordLists = intent?.getSerializableExtra("wordLists") as ArrayList<WordResult>

        //漢字、ひらがな表示
        kanjiView.text = wordLists[screenCount!!]?.word_kanji
        hiraganaView.text = wordLists[screenCount!!]?.hurigana

        wordTitle.text = "$wordLevle 単語学習"
        wordCount.text = "${screenCount + 1} /${wordLists.size}"


        //progressBar設定
        if (screenCount != null) {
            if (chooseTime != null) {
                screenCount++
                TimerFun(wordLevle, chooseTime, screenCount, wordLists)
            }
        }

        //HomeButton設定
        Home.setOnClickListener {
            mWorker?.interrupt()
            val wordLearning = Intent(this, MainActivity::class.java)
            startActivity(wordLearning)
            finish()
        }
    }

    fun TimerFun(
        wordLevle: String?,
        chooseTime: Int,
        screenCount: Int,
        wordLists: ArrayList<WordResult>
    ) {

        mWorker = Thread {
            try {
                var screenTime = (chooseTime + 1) * 10
                progressBar.max = screenTime
                var i = 0
                while (i < screenTime) {
                    i++
                    // 50ミリ秒間待機
                    // これを設置する理由は、ないと一瞬で終わっちゃうから...
                    android.os.SystemClock.sleep(60)
                    Thread.sleep(2L)
                    // メインスレッドに接続してからUI処理
                    handler.post {
                        // progressで現在の進捗率を更新
                        progressBar.progress = i
                    }
                }
                handler.post {
                    if (screenCount < wordLists.size) {
                        returnScreen(wordLevle, chooseTime, screenCount, wordLists)
                    } else {
                        wordTestScreen(wordLevle, wordLists)
                    }
                }
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
                Log.d("InterruptedException", ex.toString())
            }
        }
        mWorker?.start()
    }

    /*単語学習画面を表示*/
    fun returnScreen(
        wordLevle: String?,
        chooseTime: Int,
        screenCount: Int,
        wordLists: ArrayList<WordResult>
    ) {
        val wordLearning = Intent(this, WordLearning::class.java)
        wordLearning.putExtra("wordLever", wordLevle)
        wordLearning.putExtra("chooseTime", chooseTime)
        wordLearning.putExtra("screenCount", screenCount)
        wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
        startActivity(wordLearning)
        finish()
    }

    fun wordTestScreen(wordLevle: String?, wordLists: ArrayList<WordResult>) {
        val wordLearning = Intent(this, WordTest::class.java)
        wordLearning.putExtra("wordLever", wordLevle)
        wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
        startActivity(wordLearning)
        finish()
    }
}