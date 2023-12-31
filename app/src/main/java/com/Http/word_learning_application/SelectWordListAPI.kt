package com.Http.word_learning_application

import android.util.Log
import com.example.word_learning_application.WordResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class SelectWordListAPI {
    fun wordSelect(wordLevel: String): ArrayList<WordResult> {
        var wordSelectReturn = ArrayList<WordResult>()
        var baseurl = HttpSpringHelp.getRetrofit().create(WordAPI::class.java)
        baseurl?.getWordList(wordLevel)?.enqueue(object : Callback<ArrayList<WordResult>> {
            override fun onResponse(
                call: Call<ArrayList<WordResult>>,
                response: Response<ArrayList<WordResult>>
            ) {
                var bodyList = response.body()!!
                for (body in bodyList) {
                    wordSelectReturn.add(
                        WordResult(
                            body.word_id,
                            body.word_kanji,
                            body.hurigana,
                            body.Test1,
                            body.Test2,
                            0,
                            body.word_english,
                            body.word_korea
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ArrayList<WordResult>>, t: Throwable) {
                t.printStackTrace()
            }
        })
        Log.d("return wordSelectReturn", wordSelectReturn.toString())
        return wordSelectReturn
    }


    fun wordSelectThread(wordLevel: String): ArrayList<WordResult> {
        var wordListSelect = ArrayList<WordResult>()
            thread {
                var response = HttpSpringHelp.getRetrofit().create(WordAPI::class.java).getWordList(wordLevel).execute()
                if (response.isSuccessful) {
                    var bodyList = response.body()!!
                    for (body in bodyList) {
                        wordListSelect.add(
                            WordResult(
                                body.word_id,
                                body.word_kanji,
                                body.hurigana,
                                body.Test1,
                                body.Test2,
                                0,
                                body.word_english,
                                body.word_korea

                            )
                        )
                    }
                }
                Log.d("result wordSelectList", wordListSelect.toString())
            }
        return wordListSelect
    }


}