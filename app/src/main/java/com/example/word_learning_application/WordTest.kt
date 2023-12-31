package com.example.word_learning_application

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_word_learning.progressBar
import kotlinx.android.synthetic.main.activity_word_test.*
import java.util.concurrent.atomic.AtomicBoolean

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class WordTest : AppCompatActivity() {
    // UI処理をするためにHandlerクラス生成
    private val handler = Handler(Looper.getMainLooper())
    var running = AtomicBoolean(false)
    var wordSwitch = true
    var mWorker: Thread? = null

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_test)

        //AD
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        var wordLevle = intent.getStringExtra("wordLever")
        var screenCount: Int? = intent?.getIntExtra("screenCount", 0)?.toInt()
        var wordLists = intent?.getSerializableExtra("wordLists") as ArrayList<WordResult>

        /*学習言語*/
        val wordLanguage = intent.getStringExtra("language")

        var wordanswer = wordLists[screenCount!!]?.hurigana

        val but1 = findViewById<Button>(R.id.button1)
        val but2 = findViewById<Button>(R.id.button2)
        val but3 = findViewById<Button>(R.id.button3)
        val butarray = arrayOf(but1, but2, but3)


        wordTitle.text = "$wordLevle 単語学習"
        wordCount.text = "${screenCount!! + 1} /${wordLists.size}"

        //漢字、ひらがな表示
        TestKanji.text = wordLists[screenCount!!]?.word_kanji

        var randomTest = arrayListOf<String>()
        wordLists[screenCount!!]?.hurigana?.let { randomTest.add(it) }
        wordLists[screenCount!!]?.Test1?.let { randomTest.add(it) }
        wordLists[screenCount!!]?.Test2?.let { randomTest.add(it) }
        randomTest = randomTest.shuffled() as ArrayList<String>

        but1.text = randomTest[0]
        but2.text = randomTest[1]
        but3.text = randomTest[2]

        //thread実施
        TimerFun(but1, but2, but3, wordLists, screenCount, wordanswer, wordLevle,wordLanguage)

        but1.setOnClickListener {
            handler.post {
                wordSwitch = false
                btnCheck(butarray, wordanswer, but1, wordLists)
                stopTime()
                returnScreen(wordLevle, screenCount, wordLists, wordLanguage)
            }
        }
        but2.setOnClickListener {
            handler.post {
                wordSwitch = false
                btnCheck(butarray, wordanswer, but2, wordLists)
                stopTime()
                returnScreen(wordLevle, screenCount, wordLists, wordLanguage)
            }
        }
        but3.setOnClickListener {
            handler.post {
                wordSwitch = false
                btnCheck(butarray, wordanswer, but3, wordLists)
                stopTime()
                returnScreen(wordLevle, screenCount, wordLists, wordLanguage)
            }
        }

        Home.setOnClickListener {
            mWorker?.interrupt()
            val wordLearning = Intent(this, MainActivity::class.java)
            wordLearning.putExtra("DBcheck", false)
            wordLearning.putExtra("language", wordLanguage)
            startActivity(wordLearning)
            finish()
        }
    }


    /*単語学習画面を表示*/
    fun returnScreen(
        wordLevle: String?,
        screenCount: Int,
        wordLists: ArrayList<WordResult>,
        wordLanguage: String?
    ) {
        if (screenCount < wordLists.size - 1) {
            val wordTest = Intent(this, WordTest::class.java)
            wordTest.putExtra("wordLever", wordLevle)
            wordTest.putExtra("screenCount", screenCount + 1)
            wordTest.putParcelableArrayListExtra("wordLists", wordLists)
            wordTest.putExtra("language", wordLanguage)
            startActivity(wordTest)
            finish()
        } else {
            wordTestResults(wordLevle, wordLists, wordLanguage)
        }
    }

    fun wordTestResults(wordLevle: String?, wordLists: ArrayList<WordResult>, wordLanguage: String?) {
        val testResults = Intent(this, TestResults::class.java)
        testResults.putExtra("wordLever", wordLevle)
        testResults.putParcelableArrayListExtra("wordLists", wordLists)
        testResults.putExtra("language", wordLanguage)
        startActivity(testResults)
        finish()
    }

    fun btnCheck(
        butList: Array<Button>,
        wordanswer: String?,
        butChoose: Button,
        wordLists: ArrayList<WordResult>,
    ) {
        if (wordanswer == butChoose.text) {
            wordLists.forEach { word ->
                if (word.hurigana == wordanswer) word.word_choose = 1
            }
            butChoose.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green, theme))
        } else {
            for (but in butList) {
                if (wordanswer == but.text) {
                    but.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.yellow, theme))
                }
            }
            butChoose.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Red, theme))
        }
    }

    fun noClickBtn(but1: Button, but2: Button, but3: Button, wordanswer: String?) {
        if (wordanswer == but1.text) {
            but1.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.yellow, theme))
        }
        if (wordanswer == but2.text) {
            but2.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.yellow, theme))
        }
        if (wordanswer == but3.text) {
            but3.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.yellow, theme))
        }
    }

    fun stopTime() {
        SystemClock.sleep(70)
        mWorker?.interrupt()
    }

    fun TimerFun(
        but1: Button,
        but2: Button,
        but3: Button,
        wordLists: ArrayList<WordResult>,
        screenCount: Int,
        wordanswer: String?,
        wordLevle: String?,
        wordLanguage: String?,
    ) {

        mWorker = Thread {
            try {
                running.set(true)
                var screenTime = 60
                progressBar.max = screenTime
                var i = 0
                while (i < screenTime) {
                    i++
                    // 60ミリ秒間待機
                    // これを設置する理由は、ないと一瞬で終わっちゃうから...
                    SystemClock.sleep(60)
                    Thread.sleep(2L)
                    // メインスレッドに接続してからUI処理
                    handler.post {
                        // progressで現在の進捗率を更新
                        progressBar.progress = i
                    }
                }
                if (wordSwitch) {
                    handler.post {
                        noClickBtn(but1, but2, but3, wordanswer)
                        SystemClock.sleep(60)
                        returnScreen(wordLevle, screenCount, wordLists, wordLanguage)
                    }
                }
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
                Log.d("InterruptedException", ex.toString())
            }
        }
        mWorker?.start()
    }
}//thread
