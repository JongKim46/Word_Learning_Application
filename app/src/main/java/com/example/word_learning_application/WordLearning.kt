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
        /*選択した学習レベル取得*/
        var wordLevle = intent?.getStringExtra("wordLever")
        /*選択した学習時間取得*/
        var chooseTime = intent?.getIntExtra("chooseTime", 0)
        /*何番目の学習画面なのか取得*/
        var screenCount: Int? = intent?.getIntExtra("screenCount", 0)?.toInt()
        /*Jsonで取得したデータ*/
        var wordLists = intent?.getSerializableExtra("wordLists") as ArrayList<WordResult>

        //漢字、ひらがな表示
        kanjiView.text = wordLists[screenCount!!]?.word_kanji
        hiraganaView.text = wordLists[screenCount!!]?.hurigana

        /*タイトル設定*/
        wordTitle.text = "$wordLevle 単語学習"
        /*取得した全データと学習の順番を表示*/
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
            wordLearning.putExtra("DBcheck", false)
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
                    /*学習が終わったか確認。*/
                    if (screenCount < wordLists.size) {
                        /*学習が終わっていなければ学習画面を表示*/
                        returnScreen(wordLevle, chooseTime, screenCount, wordLists)
                    } else {
                        /*学習が終わったらテスト画面を表示*/
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
    /*テスト画面を表示*/
    fun wordTestScreen(wordLevle: String?, wordLists: ArrayList<WordResult>) {
        val wordLearning = Intent(this, WordTest::class.java)
        wordLearning.putExtra("wordLever", wordLevle)
        wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
        startActivity(wordLearning)
        finish()
    }
}