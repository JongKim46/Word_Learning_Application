package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.io.BufferedReader
import java.io.InputStreamReader

class Loading_Screen : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)


        var DBcheck = intent?.getBooleanExtra("DBcheck", true)

        //LocalDB TABLE名設定
        if (DBcheck!!) {
            var wordSQL = readSQL()
            var dbHelper = LocalDBHelper(this, "WordTable.db", null, 1)
            var database = dbHelper.writableDatabase
            dbHelper.onCreate(database)
            dbHelper.insert(database, wordSQL)
        }

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }, SPLASH_TIME_OUT)
    }

    private fun readSQL(): ArrayList<String> {
        var wordSQl = resources.openRawResource(R.raw.wordsql)

        var str = ArrayList<String>()
        val sb = BufferedReader(InputStreamReader(wordSQl, "UTF-8"))
        while (true) {
            val line = sb.readLine() ?: break
            str.add(line)
        }
        return str
    }
}