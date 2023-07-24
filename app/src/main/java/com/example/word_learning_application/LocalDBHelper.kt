package com.example.word_learning_application

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
                "WORD_KANJI VARCHAR(10), " +
                "WORD_HURIGANA VARCHAR(15), " +
                "WORD_HURIGANA_TEST1 VARCHAR(15), " +
                "WORD_HURIGANA_TEST2 VARCHAR(15)) "
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insert(db: SQLiteDatabase,txt:String){
        var sql = " INSERT INTO MYTABLE(TXT) " +
                " VALUES('${txt}')"

        db.execSQL(sql)

    }

    fun select(db: SQLiteDatabase, wordLevle: String, wordLists: ArrayList<WordResult>) : ArrayList<WordResult>?{
        var levelSql = ""
        when(wordLevle){
            "N5" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N5 = 1 ORDER BY RAND() LIMIT 10"
            "N4" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N4 = 1 ORDER BY RAND() LIMIT 10"
            "N3" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N3 = 1 ORDER BY RAND() LIMIT 10"
            "N2" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N2 = 1 ORDER BY RAND() LIMIT 10"
            "N1" -> levelSql = "SELECT DISTINCT * FROM WORD_TABLE WHERE WORD_LEVEL_N1 = 1 ORDER BY RAND() LIMIT 10"
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
                    0
                )
            )
        }

        return wordLists
    }

    fun delete(db:SQLiteDatabase,txt:String){

    }


}