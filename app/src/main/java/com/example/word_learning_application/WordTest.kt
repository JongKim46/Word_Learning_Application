package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_word_test.*
import kotlin.concurrent.thread



class WordTest : AppCompatActivity() {
    // UI処理をするためにHandlerクラス生成
    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_test)

        var wordLevle = intent.getStringExtra("wordLever")
        var screenCount: Int? = intent?.getIntExtra("screenCount", 0)?.toInt()
        var wordLists = intent?.getSerializableExtra("wordLists") as ArrayList<WordResult>

        val bnt1 = findViewById<Button>(R.id.button1)
        val bnt2 = findViewById<Button>(R.id.button2)
        val bnt3 = findViewById<Button>(R.id.button3)

        wordTitle.text = "$wordLevle 単語学習"
        wordCount.text = "${screenCount!!+1} /${wordLists.size}"

        //漢字、ひらがな表示
        TestKanji.text = wordLists[screenCount!!]?.word_kanji

        var randomTest = arrayListOf<String>()
        wordLists[screenCount!!]?.hurigana?.let { randomTest.add(it) }
        wordLists[screenCount!!]?.Test1?.let { randomTest.add(it) }
        wordLists[screenCount!!]?.Test2?.let { randomTest.add(it) }
        randomTest = randomTest.shuffled() as ArrayList<String>

        bnt1.text = randomTest[0]
        bnt2.text = randomTest[1]
        bnt3.text = randomTest[2]

        Home.setOnClickListener {
            val wordLearning = Intent(this, MainActivity::class.java)
            startActivity(wordLearning)
            finish()
        }
    }

    fun TimerFun(wordLevle: String?, chooseTime: Int, screenCount: Int) {

        var screenTime = 60
        // テキストをリセット
        thread {
            progressBar.max = screenTime
            var i = 0
            while (i < screenTime) {
                i++
                // 50ミリ秒間待機
                // これを設置する理由は、ないと一瞬で終わっちゃうから...
                SystemClock.sleep(60)
                // メインスレッドに接続してからUI処理
                handler.post {
                    // progressで現在の進捗率を更新
                    progressBar.progress = i
                }
            }
            handler.post {
                if(screenCount <= 2){
                    returnScreen(wordLevle, chooseTime, screenCount)
                }else{
                    wordTestScreen(wordLevle)
                }
            }
        }
    }

    /*単語学習画面を表示*/
    fun returnScreen(wordLevle: String?, chooseTime: Int, screenCount: Int) {
        val wordLearning = Intent(this, WordLearning::class.java)
        wordLearning.putExtra("wordLever", wordLevle)
        wordLearning.putExtra("chooseTime", chooseTime)
        wordLearning.putExtra("screenCount", screenCount)
        startActivity(wordLearning)
        finish()
    }

    fun wordTestScreen(wordLevle: String?){
        val wordLearning = Intent(this, WordTest::class.java)
        wordLearning.putExtra("wordLever", wordLevle)
        startActivity(wordLearning)
        finish()
    }

}