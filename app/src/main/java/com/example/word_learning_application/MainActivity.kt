package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.Http.word_learning_application.SelectWordListAPI
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val secondScreen = Intent(this, SecondChooseActivity::class.java)

        var DBcheck = intent?.getBooleanExtra("DBcheck", true)

        //LocalDB TABLE名設定
        if (DBcheck!!) {
            var wordSQL = readSQL()
            var dbHelper = LocalDBHelper(this, "WordTable.db", null, 1)
            var database = dbHelper.writableDatabase
            dbHelper.onCreate(database)
            dbHelper.insert(database, wordSQL)
        }

        N5_button.setOnClickListener {
            val wordLever = "N1"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N4_button.setOnClickListener {
            val wordLever = "N4"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N3_button.setOnClickListener {
            val wordLever = "N3"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N2_button.setOnClickListener {
            val wordLever = "N2"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
        N1_button.setOnClickListener {
            val wordLever = "N1"
            secondScreen.putExtra("wordLever", wordLever)
            startActivity(secondScreen)
        }
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

