package com.oguzhanck.vocabularyapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.local.WordViewModel
import com.oguzhanck.vocabularyapp.model.ListEntity
import kotlinx.android.synthetic.main.activity_add_word.*

class AddWordActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]

        getWord()
    }


    private fun getWord() {
        addButton.setOnClickListener {
            wordViewModel.insertWordDetailList(
                ListEntity(
                    0,
                    wordTypeEditText.text.toString(),
                    wordEditText.text.toString(),
                    definitionEditText.text.toString(),
                    synonymsEditText.text.toString(),
                    antonymsEditText.text.toString(),
                    sentenceEditText.text.toString()
                )
            )
            Toast.makeText(this, "Your word is added.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AddWordActivity, MainActivity::class.java)
            overridePendingTransition(0, 0)
            startActivity(intent)
        }

    }
}