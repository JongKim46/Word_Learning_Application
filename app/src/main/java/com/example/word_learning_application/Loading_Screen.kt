package com.example.word_learning_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_loading_screen.countView

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Loading_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var _progressBar = 0
        var _count = 0

        var mainthread = Thread {
            var DBcheck = intent?.getBooleanExtra("DBcheck", true)

            //LocalDB TABLE名設定
            if (DBcheck!!) {
                var dbHelper = LocalDBHelper(this, "WordTable.db", null, 1)
                var database = dbHelper.writableDatabase
                //dbHelper.delete(database)
                dbHelper.onCreate(database)

                var wordSQl1 = resources.openRawResource(R.raw.jlpt_n1)
                var wordSQl2 = resources.openRawResource(R.raw.jlptn2)
                var wordSQl3 = resources.openRawResource(R.raw.jlpt_n3)
                var wordSQl45 = resources.openRawResource(R.raw.jlpt_n4n5)

                var _wordSqlList = arrayOf(wordSQl1, wordSQl2,wordSQl3,wordSQl45)
                for(wordSql in _wordSqlList){
                    _progressBar += 25
                    _count += 1
                    handler.post{
                        progressBar.progress = _progressBar
                        countView.text = "${_count}/4"
                    }
                    Log.d("TABLE WORD _wordSqlList", "$wordSql")
                    var wordSQL = readSQL(wordSql)
                    var success = dbHelper.insert(database, wordSQL)
                }
                handler.post{
                    startActivity(Intent(this, SelectLanguage::class.java))
                    finish()
                }
            }
        }
        mainthread.start()
    }

    private fun readSQL(wordSQl: InputStream): ArrayList<String> {
        var str = ArrayList<String>()
        val sb = BufferedReader(InputStreamReader(wordSQl, "UTF-8"))
        while (true) {
            val line = sb.readLine() ?: break
            str.add(line)
        }
        return str
    }
}

