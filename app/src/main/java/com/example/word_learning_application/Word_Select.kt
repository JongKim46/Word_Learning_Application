package com.example.word_learning_application

import android.os.StrictMode
import java.sql.*

class Word_Select {

    fun wordSelect(wordLevel: String): ArrayList<WordResult> {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        var wordResult = ArrayList<WordResult>()
        var url = "jdbc:mysql://localhost:3306/word_learning?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&enabledTLSProtocols=TLSv1.2"
        var user = "root"
        var password = "root"

        println("connect reid")
        println("wordLevel : " + wordLevel)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            conn = DriverManager.getConnection(
                url, user, password
            )
            println("connect ok")
            var sql: String =
                "SELECT DISTINCT WORD_ID,WORD_KANJI,WORD_HURIGANA,WORD_HURIGANA_TEST1,WORD_HURIGANA_TEST2 FROM word_table"

            stmt = conn.createStatement()
            rs = stmt.executeQuery(sql)

            while (rs.next()) {
                wordResult.add(
                    WordResult(
                        rs.getInt("WORD_ID"),
                        rs.getString("WORD_KANJI"),
                        rs.getString("WORD_HURIGANA"),
                        rs.getString("WORD_HURIGANA_TEST1"),
                        rs.getString("WORD_HURIGANA_TEST2"),
                        0
                    )
                )
            }

        } catch (e: Exception) {
            println(e)
        } finally {
            rs?.close()
            stmt?.close()
            conn?.close()
        }
        return wordResult
    }
}