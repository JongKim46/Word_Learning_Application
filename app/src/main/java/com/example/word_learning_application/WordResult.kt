package com.example.word_learning_application

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordResult(
    /*単語ID*/
    var word_id: Int,
    /*単語漢字*/
    var word_kanji: String,
    /*単語ふりがな*/
    var hurigana: String,
   /* テストふりがな1*/
    var Test1: String,
    /*テストふりがな2*/
    var Test2: String,
    /*テスト中に選択した回答に対する判断*/
    var word_choose: Int,
) : Parcelable
{
    override fun toString(): String {
        return "word_id:$word_id, word_kanji:$word_kanji, hurigana:$hurigana," +
                " Test1:$Test1, Test2:$Test2, word_choose:$word_choose"
    }
}