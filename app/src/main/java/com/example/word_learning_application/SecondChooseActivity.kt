package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_second_choose.*
import org.json.JSONObject
import java.io.IOException


class SecondChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_choose)
        /*DB成城*/
        var dbHelper = LocalDBHelper(this, "WordTable.db",null,1)

        /*学習Level*/
        val wordLevle = intent.getStringExtra("wordLever")
        /*タイトル設定*/
        wordTitle.text = wordLevle + "単語学習時間選択"

        val wordLearning = Intent(this, WordLearning::class.java)
        /*選択した学習レベル取得*/
        wordLearning.putExtra("wordLever", wordLevle)
        /*何番目の学習画面なのか取得*/
        wordLearning.putExtra("screenCount", 0)
        var wordLists = arrayListOf<WordResult>()

        var database = dbHelper.writableDatabase

        //3秒選択
        time_3.setOnClickListener {
            wordLists = dbHelper.select(database, wordLevle!!, wordLists)!!
            //wordLists = selectWord(wordLevle!!, wordLists)
            wordLearning.putExtra("chooseTime", 3)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }
        //5秒選択
        time_5.setOnClickListener {
            wordLists = dbHelper.select(database, wordLevle!!, wordLists)!!
            wordLearning.putExtra("chooseTime", 5)
            wordLearning.putParcelableArrayListExtra("wordLists", wordLists)
            startActivity(wordLearning)
        }
        //10秒選択
        time_10.setOnClickListener {
            wordLists = dbHelper.select(database, wordLevle!!, wordLists)!!
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

    fun selectWord(wordLevel: String, wordLists: ArrayList<WordResult>): ArrayList<WordResult> {
       var wordSelectList = WordSelect().selectWord(wordLevel)

        /*Jsonファイル取得*/
        val jsonString = getJsonDataFromAsset(wordLevel)
        /*取得したJsonファイルをList化*/
        var wordList = getResultWord(wordLists, jsonString!!)
        Log.d("JsonWordLists ", wordLists.toString())
        return wordList
    }

    fun getJsonDataFromAsset(wordLevel: String): String? {
        /*Jsonファイル取得*/
        val jsonString: String
        var jsonFile: String = ""
        when(wordLevel){
            "N5" -> jsonFile = "WordListN5.json"
            "N4" -> jsonFile = "WordListN4.json"
            "N3" -> jsonFile = "WordListN3.json"
            "N2" -> jsonFile = "WordListN2.json"
            "N1" -> jsonFile = "WordListN1.json"
        }
        try {
            jsonString = assets.open(jsonFile)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getResultWord(wordLists: ArrayList<WordResult>, jsonString: String): ArrayList<WordResult> {
        /*取得したJsonファイルをList化*/
        val dataList = JSONObject(jsonString).getJSONArray("WORD_TABLE")

        for (index in 0 until dataList.length()) {
            val jsonObject = dataList.getJSONObject(index)
            wordLists.add(
                WordResult(
                    jsonObject.getInt("WORD_ID"),
                    jsonObject.getString("WORD_KANJI"),
                    jsonObject.getString("WORD_HURIGANA"),
                    jsonObject.getString("WORD_HURIGANA_TEST1"),
                    jsonObject.getString("WORD_HURIGANA_TEST2"),
                    0
                )
            )
        }

        return wordLists
    }
}