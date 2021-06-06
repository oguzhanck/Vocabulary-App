package com.oguzhanck.vocabularyapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.local.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notification.*


class NotificationActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]
        val word: String? = intent.getStringExtra("word")
        val status: String? = intent.getStringExtra("status")

        notificationWordTextView.text = word

        remindLaterButton.setOnClickListener {
            overridePendingTransition(0, 0)
            startActivity(Intent(this@NotificationActivity, MainActivity::class.java))
        }

        memorizedButton.setOnClickListener { itView ->
            wordViewModel.allWordDetailList.observe(this, { it ->
                for (i in it) {
                    if (word == i.word) {
                        wordViewModel.deleteWordDetailList(i)
                    }
                }
            })
            overridePendingTransition(0, 0)
            startActivity(Intent(this@NotificationActivity, MainActivity::class.java))
        }
    }
}