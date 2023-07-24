package com.example.word_learning_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WordSelect {
    public ArrayList<WordResult> selectWord(String wordLevel) {
        ArrayList<WordResult> wordList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/word_learning";
        String user = "root";
        String password = "root";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        switch (wordLevel){
            case "N5":
                sql = "SELECT DISTINCT WORD_ID,WORD_KANJI,WORD_HURIGANA,WORD_HURIGANA_TEST1,WORD_HURIGANA_TEST2 FROM word_table";
                break;
            case "N4":
                sql = "SELECT DISTINCT WORD_ID,WORD_KANJI,WORD_HURIGANA,WORD_HURIGANA_TEST1,WORD_HURIGANA_TEST2 FROM word_table";
                break;
            case "N3":
                sql = "SELECT DISTINCT WORD_ID,WORD_KANJI,WORD_HURIGANA,WORD_HURIGANA_TEST1,WORD_HURIGANA_TEST2 FROM word_table";
                break;
            case "N2":
                sql = "SELECT DISTINCT WORD_ID,WORD_KANJI,WORD_HURIGANA,WORD_HURIGANA_TEST1,WORD_HURIGANA_TEST2 FROM word_table";
                break;
            case "N1":
                sql = "SELECT DISTINCT WORD_ID,WORD_KANJI,WORD_HURIGANA,WORD_HURIGANA_TEST1,WORD_HURIGANA_TEST2 FROM word_table";
                break;
        }

        System.out.println("DB Start : " + sql);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 接続
            con = DriverManager.getConnection(url, user, password);
            // パラメータ付きSQL文をDBに送るためのオブジェクト生成
            pstmt = con.prepareStatement(sql);
            // SQL文の実行(データ取得)
            rs = pstmt.executeQuery();

            // データ取得したレコードの表示
            while (rs.next()) {
                wordList.add(
                        new WordResult(
                                rs.getInt("WORD_ID"),
                                rs.getString("WORD_KANJI"),
                                rs.getString("WORD_HURIGANA"),
                                rs.getString("WORD_HURIGANA_TEST1"),
                                rs.getString("WORD_HURIGANA_TEST2"),
                                0
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return wordList;
    }
}
