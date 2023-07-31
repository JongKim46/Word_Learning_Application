package com.Http.word_learning_application

import android.util.Log
import com.example.word_learning_application.WordResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectWordListAPI {
    var wordSelectLists = ArrayList<WordResult>()
    var wordSelectReturn = ArrayList<WordResult>()

    fun wordSelect(wordLevel: String): ArrayList<WordResult> {
        var baseurl = HttpSpringHelp.getRetrofit().create(WordAPI::class.java)
        baseurl?.getWordList(wordLevel)?.enqueue(object : Callback<ArrayList<WordResult>> {
            override fun onResponse(
                call: Call<ArrayList<WordResult>>,
                response: Response<ArrayList<WordResult>>
            ) {
                var bodyList = response.body()!!
                for (body in bodyList) {
                    wordSelectLists.add(
                        WordResult(
                            body.word_id,
                            body.word_kanji,
                            body.hurigana,
                            body.Test1,
                            body.Test2,
                            0
                        )
                    )
                }

                wordSelectReturn.addAll(wordSelectLists)
                Log.d("body1", wordSelectReturn.toString())
                wordSelectReturn = wordSelectLists
                Log.d("body2", wordSelectReturn.toString())
            }

            override fun onFailure(call: Call<ArrayList<WordResult>>, t: Throwable) {
                t.printStackTrace()
            }
        })
        Log.d("return wordSelectReturn", wordSelectReturn.toString())
        return wordSelectReturn
    }


    fun wordSelectJson(wordLevel: String): ArrayList<WordResult> {
        var baseurl = HttpSpringHelp.getRetrofit().create(WordAPI::class.java)
        baseurl?.getWordList(wordLevel)?.enqueue(object : Callback<ArrayList<WordResult>> {
            override fun onResponse(
                call: Call<ArrayList<WordResult>>,
                response: Response<ArrayList<WordResult>>
            ) {
                var bodyList = response.body()!!
                for (body in bodyList) {
                    wordSelectLists.add(
                        WordResult(
                            body.word_id,
                            body.word_kanji,
                            body.hurigana,
                            body.Test1,
                            body.Test2,
                            0
                        )
                    )
                }

                Log.d("body", wordSelectLists.toString())

            }

            override fun onFailure(call: Call<ArrayList<WordResult>>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return wordSelectLists
    }
}