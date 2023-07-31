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
                Log.d("body", wordSelectLists.toString())

            }

            override fun onFailure(call: Call<ArrayList<WordResult>>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return wordSelectLists
    }

    fun wordSelectJson(wordLevel: String): ArrayList<WordResult> {
        var baseurl = HttpSpringHelp.getRetrofit().create(WordAPI::class.java)
        baseurl?.getWordList(wordLevel)?.enqueue(object : Callback<ArrayList<WordResult>> {
            override fun onResponse(
                call: Call<ArrayList<WordResult>>,
                response: Response<ArrayList<WordResult>>
            ) {
                var dataList = response.body()
                for (index in 0 until dataList!!.size) {
                    Log.d("JsonbodyFor", dataList[index].toString())
                    wordSelectLists.add(
                        WordResult(
                            dataList[index].word_id,
                            dataList[index].word_kanji,
                            dataList[index].hurigana,
                            dataList[index].Test1,
                            dataList[index].Test2,
                            0
                        )
                    )
                }
                Log.d("Jsonbody1", wordSelectLists.toString())
                wordSelectReturn = wordSelectLists.toCollection(mutableListOf()) as ArrayList<WordResult>
            }
            override fun onFailure(call: Call<ArrayList<WordResult>>, t: Throwable) {
                t.printStackTrace()
            }
        })
        Log.d("Jsonbody2", wordSelectLists.toString())
        return wordSelectReturn
    }
}