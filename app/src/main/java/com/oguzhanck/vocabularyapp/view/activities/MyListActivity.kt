package com.oguzhanck.vocabularyapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.local.WordViewModel
import com.oguzhanck.vocabularyapp.model.ListEntity
import com.oguzhanck.vocabularyapp.view.adapters.ListAdapter
import kotlinx.android.synthetic.main.activity_my_list.*


class MyListActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)
        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]

        wordViewModel.allWordDetailList.observe(this, { it ->
            wordList_recycleView.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(this@MyListActivity, LinearLayoutManager.VERTICAL, false)

                adapter = ListAdapter(it, object : ListAdapter.OnClickListenerWordList {
                    override fun onItemClick(word: ListEntity) {
                        val intent = Intent(this@MyListActivity, NotificationActivity::class.java)
                        intent.putExtra("word", word.word)
                        overridePendingTransition(0, 0)
                        startActivity(intent)
                    }

                })
            }
        })

    }
}