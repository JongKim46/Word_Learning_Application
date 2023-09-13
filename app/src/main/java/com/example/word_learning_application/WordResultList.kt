package com.example.word_learning_application

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class WordResultList(
    val context: Context,
    val wordLists: ArrayList<WordResult>,
    val wordLanguage: String?
): BaseAdapter() {

    override fun getCount(): Int {
        return wordLists.size
    }

    override fun getItem(position: Int): Any {
      return wordLists[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        /*LayoutInflaterは、itemをAdapterで使用するViewに膨らませる(inflate)役割を果たす。*/
        val view: View = LayoutInflater.from(context).inflate(R.layout.word_test_item, null)

        /*上で生成されたviewをword_test_item.xmlファイルの各Viewと連結する過程だ。*/
        val wordKanji = view.findViewById<TextView>(R.id.wordKanji)
        val wordHiraganar = view.findViewById<TextView>(R.id.wordHiraganar)
        val wordMeaning = view.findViewById<TextView>(R.id.wordMeaning)
        val bookmarck = view.findViewById<TextView>(R.id.wordMeaning)

        /*試験結果の判断及びカラー表示*/
        val word = wordLists[position]
        if(word.word_choose == 1){
            wordKanji.setBackgroundResource(R.color.green)
            wordHiraganar.setBackgroundResource(R.color.green)
            bookmarck.setBackgroundResource(R.color.green)
        }else{
            wordKanji.setBackgroundResource(R.color.yellow)
            wordHiraganar.setBackgroundResource(R.color.yellow)
            bookmarck.setBackgroundResource(R.color.yellow)
        }
        wordKanji.text = word.word_kanji
        wordHiraganar.text = word.hurigana
        if(wordLanguage == "kr"){
            wordMeaning.text = word.word_korea
        }else{
            wordMeaning.text = word.word_english
        }
        return view
    }

}