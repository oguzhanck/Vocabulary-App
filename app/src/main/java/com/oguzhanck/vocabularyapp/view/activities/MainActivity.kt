package com.oguzhanck.vocabularyapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.local.WordViewModel
import com.oguzhanck.vocabularyapp.services.NotificationService
import com.oguzhanck.vocabularyapp.services.Words
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]

        // ADD WORDS TO DATABASE ON FIRST START //
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)
        if (firstStart)
            showStartDialog(wordViewModel)

        // Transition between Activities
        transition()

    }

    private fun showStartDialog(viewModel: WordViewModel) {
        val words = Words()
        words.addDataToDatabase(viewModel)

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }

    private fun transition() {
        wordsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, WordsActivity::class.java)
            overridePendingTransition(0, 0)
            startActivity(intent)
        }
        addWordsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddWordActivity::class.java)
            overridePendingTransition(0, 0)
            startActivity(intent)
        }
        quizButton.setOnClickListener {
            val intent = Intent(this@MainActivity, QuizActivity::class.java)
            overridePendingTransition(0, 0)
            startActivity(intent)
        }
        myListButton.setOnClickListener {
            val check = wordViewModel.checkEmptyList()
            if (check != 0) {
                val intent = Intent(this@MainActivity, MyListActivity::class.java)
                overridePendingTransition(0, 0)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Your List is Empty", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onStop() {
        super.onStop()

        val check = wordViewModel.checkEmptyList()
        if (check != 0)
            getWordFromList()
    }

    private fun getWordFromList() {
        wordViewModel.allWordDetailList.observe(this, { words ->
            if (words != null) {
                startService(
                    Intent(this, NotificationService::class.java).putExtra(
                        "word",
                        words.random().word
                    )
                )
            }
        })
    }

}