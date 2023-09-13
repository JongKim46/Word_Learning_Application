package com.example.word_learning_application

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordResult(
    /*単語ID*/
    @SerializedName("WORD_ID")var word_id: Int,
    /*単語漢字*/
    @SerializedName("WORD_KANJI")var word_kanji: String,
    /*単語ふりがな*/
    @SerializedName("WORD_HURIGANA")var hurigana: String,
    /* テストふりがな1*/
    @SerializedName("WORD_HURIGANA_TEST1")var Test1: String,
    /*テストふりがな2*/
    @SerializedName("WORD_HURIGANA_TEST2")var Test2: String,
    /*テスト中に選択した回答に対する判断*/
    var word_choose: Int,
    /*英語*/
    @SerializedName("WORD_ENGLISH")var word_english: String,
    /*韓国語*/
    @SerializedName("WORD_KOREA")var word_korea: String,

) : Parcelable
{
    override fun toString(): String {
        return "word_id:$word_id, word_kanji:$word_kanji, hurigana:$hurigana," +
                " Test1:$Test1, Test2:$Test2, word_choose:$word_choose"+
                "word_english:$word_english, word_korea:$word_korea"
    }
}