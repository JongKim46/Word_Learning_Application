package com.example.word_learning_application

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths


class LocalDBHelper(context: Context?,name:String?,factory:SQLiteDatabase.CursorFactory?,version: Int)
    : SQLiteOpenHelper(context,name,factory,version) {

    override fun onCreate(db: SQLiteDatabase?) {
        //ローカルテーブルがなければ作成
        var sql: String = " CREATE TABLE IF NOT EXISTS WORD_TABLE( " +
                "WORD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "WORD_LEVEL_N1 INT(1), " +
                "WORD_LEVEL_N2 INT(1), " +
                "WORD_LEVEL_N3 INT(1), " +
                "WORD_LEVEL_N4 INT(1), " +
                "WORD_LEVEL_N5 INT(1), " +
                "WORD_KANJI VARCHAR(100), " +
                "WORD_HURIGANA VARCHAR(100), " +
                "WORD_HURIGANA_TEST1 VARCHAR(100), " +
                "WORD_HURIGANA_TEST2 VARCHAR(100), " +
                "WORD_ENGLISH VARCHAR(100), " +
                "WORD_KOREA VARCHAR(100)) "
        db?.execSQL(sql)
        Log.d("TABLE WORD", "TABLE WORD作成完了")
        delete(db!!)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insert(db: SQLiteDatabase, wordSQL: ArrayList<String>){
        try {
            for (sql in wordSQL){
                db.execSQL(sql)
            }
            Log.d("TABLE WORD", "TABLE Insert完了")
        }catch (e: Exception){
            Log.d("TABLE WORD", "TABLE Insert失敗")
        }

    }

    fun select(db: SQLiteDatabase, wordLevle: String, wordLists: ArrayList<WordResult>) : ArrayList<WordResult>?{
        var levelSql = ""
        when(wordLevle){
            "N5" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N5 = 1 ORDER BY RANDOM() LIMIT 10"
            "N4" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N4 = 1 ORDER BY RANDOM() LIMIT 10"
            "N3" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N3 = 1 ORDER BY RANDOM() LIMIT 10"
            "N2" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N2 = 1 ORDER BY RANDOM() LIMIT 10"
            "N1" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N1 = 1 ORDER BY RANDOM() LIMIT 10"
        }

        var rs = db.rawQuery(levelSql, null)
        while (rs.moveToNext()){
            wordLists.add(
                WordResult(
                    rs.getInt(rs.getColumnIndex("WORD_ID")),
                    rs.getString(rs.getColumnIndex("WORD_KANJI")),
                    rs.getString(rs.getColumnIndex("WORD_HURIGANA")),
                    rs.getString(rs.getColumnIndex("WORD_HURIGANA_TEST1")),
                    rs.getString(rs.getColumnIndex("WORD_HURIGANA_TEST2")),
                    0,
                    rs.getString(rs.getColumnIndex("word_english")),
                    rs.getString(rs.getColumnIndex("word_korea"))
                )
            )
        }
        Log.d("select wordLists", wordLists.toString())
        return wordLists
    }

    fun delete(db:SQLiteDatabase){
        val sql = "DELETE FROM WORD_TABLE"
        db.execSQL(sql)
        Log.d("TABLE WORD", "TABLE WORDデータ削除完了")
        onUpgrade(db!!)
    }

    private fun onUpgrade(db: SQLiteDatabase) {
        val sql = "UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'WORD_TABLE'"
        db.execSQL(sql)
        Log.d("TABLE WORD", "AutoIncrement初期化完了")
    }


}