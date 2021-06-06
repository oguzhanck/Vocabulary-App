package com.oguzhanck.vocabularyapp.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.local.WordViewModel
import com.oguzhanck.vocabularyapp.model.ListEntity
import com.oguzhanck.vocabularyapp.model.WordEntity
import kotlinx.android.synthetic.main.activity_words.*

class WordsActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel
    private lateinit var wordList: List<WordEntity>
    private lateinit var nextRandomWord: WordEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)
        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]


        // CHANGE THE NEXT WORD //
        changeWord()
        // ADD THE SELECTED WORD TO LIST //
        addToList()
    }

    private fun changeWord() {
        wordViewModel.allWordDetail.observe(this, { wordsFromDatabase ->
            wordList = wordsFromDatabase

            val randomWord = wordList.random()
            wordTextView.text = randomWord.word
            wordTypeTextView.text = randomWord.wordType
            definitionTextView.text = randomWord.definition
            sentenceTextView.text = randomWord.exampleSentence
            synonymsTextView.text = randomWord.synonyms
            antonymsTextView.text = randomWord.antonyms
        })

        nextWordButton.setOnClickListener {
            var exit = true
            while (exit) {
                nextRandomWord = wordList.random()
                if (nextRandomWord.word != wordTextView.text) {
                    exit = false
                }
            }

            wordTextView.text = nextRandomWord.word
            wordTypeTextView.text = nextRandomWord.wordType
            definitionTextView.text = nextRandomWord.definition
            sentenceTextView.text = nextRandomWord.exampleSentence
            synonymsTextView.text = nextRandomWord.synonyms
            antonymsTextView.text = nextRandomWord.antonyms
        }
    }

    private fun addToList() {
        addToListButton.setOnClickListener {
            wordViewModel.insertWordDetailList(
                ListEntity(
                    0,
                    wordTypeTextView.text.toString(),
                    wordTextView.text.toString(),
                    definitionTextView.text.toString(),
                    synonymsTextView.text.toString(),
                    antonymsTextView.text.toString(),
                    sentenceTextView.text.toString()
                )
            )
            Toast.makeText(this, "Added to list.", Toast.LENGTH_SHORT).show()
        }
    }
}