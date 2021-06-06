package com.oguzhanck.vocabularyapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.local.WordViewModel
import com.oguzhanck.vocabularyapp.model.WordEntity
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_words.*

class QuizActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]


        // DROP DOWN MENU //
        spinnerBox()
    }

    private fun spinnerBox() {
        ArrayAdapter.createFromResource(
            this,
            R.array.type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            chooseSpinner.adapter = adapter
        }

        chooseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when (position) {
                    0 -> {

                    }
                    1 -> {
                        questionVisibility()
                        quiz("verb")
                    }
                    2 -> {
                        questionVisibility()
                        quiz("adverb")
                    }
                    3 -> {
                        questionVisibility()
                        quiz("adj")
                    }
                    4 -> {
                        questionVisibility()
                        quiz("phrases")
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun questionVisibility() {
        quizLayout.visibility = View.VISIBLE
        selectTextView.visibility = View.INVISIBLE
        chooseSpinner.visibility = View.INVISIBLE
    }


    private fun quiz(typeOfWord: String) {
        wordViewModel.allWordDetail.observe(this, { wordsFromDatabase ->
            val randomNumbers = (0..9).shuffled().take(10).toSet()
            val quizList: ArrayList<WordEntity> = ArrayList()
            var flag = 0

            for (i in wordsFromDatabase) {
                if (i.wordType == typeOfWord)
                    quizList.add(i)
            }
            nextQuestion(quizList, 0, wordsFromDatabase)

            var point = 0
            button1.setOnClickListener {
                if (quizList[flag].word == button1.text) {
                    point++
                    Toast.makeText(this, "Correct, Your Point is: $point", Toast.LENGTH_SHORT)
                        .show()
                }
                flag++
                if (flag == 10) {
                    Toast.makeText(this, "Point: $point", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@QuizActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                if (flag != 10) {
                    nextQuestion(quizList, flag, wordsFromDatabase)
                }
            }

            button2.setOnClickListener {
                if (quizList[flag].word == button2.text) {
                    point++
                    Toast.makeText(this, "Correct Your Point: $point", Toast.LENGTH_SHORT).show()
                }
                flag++
                if (flag == 10) {
                    Toast.makeText(this, "Point: $point", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@QuizActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                if (flag != 10) {
                    nextQuestion(quizList, flag, wordsFromDatabase)
                }
            }

            button3.setOnClickListener {
                if (quizList[flag].word == button3.text) {
                    point++
                    Toast.makeText(this, "Correct Your Point: $point", Toast.LENGTH_SHORT).show()
                }
                flag++
                if (flag == 10) {
                    Toast.makeText(this, "Point: $point/10", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@QuizActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                if (flag != 10) {
                    nextQuestion(quizList, flag, wordsFromDatabase)
                }
            }
        })
    }

    private fun nextQuestion(arrayList: ArrayList<WordEntity>, index: Int, list: List<WordEntity>) {
        synonymsTextViewQuiz.text = arrayList[index].synonyms
        antonymsTextViewQuiz.text = arrayList[index].antonyms
        questionTextView.text = arrayList[index].definition

        val randomNumbers = setOf(1, 2, 3)
        val location = randomNumbers.random()

        if (location == 1) {
            button1.text = arrayList[index].word

            var temp1 = list.random().word
            var exit1 = true
            while (exit1) {
                if (temp1 != arrayList[index].word) {
                    button2.text = temp1
                    exit1 = false
                }
                temp1 = list.random().word
            }

            var temp2 = list.random().word
            var exit2 = true
            while (exit2) {
                if (temp2 != arrayList[index].word && temp2 != temp1) {
                    button3.text = temp2
                    exit2 = false
                }
                temp2 = list.random().word
            }
        } else if (location == 2) {
            var temp1 = list.random().word
            var exit1 = true
            while (exit1) {
                if (temp1 != arrayList[index].word) {
                    button1.text = temp1
                    exit1 = false
                }
                temp1 = list.random().word
            }

            button2.text = arrayList[index].word

            var temp2 = list.random().word
            var exit2 = true
            while (exit2) {
                if (temp2 != arrayList[index].word && temp2 != temp1) {
                    button3.text = temp2
                    exit2 = false
                }
                temp2 = list.random().word
            }
        } else if (location == 3) {
            var temp1 = list.random().word
            var exit1 = true
            while (exit1) {
                if (temp1 != arrayList[index].word) {
                    button1.text = temp1
                    exit1 = false
                }
                temp1 = list.random().word
            }

            var temp2 = list.random().word
            var exit2 = true
            while (exit2) {
                if (temp2 != arrayList[index].word && temp2 != temp1) {
                    button2.text = temp2
                    exit2 = false
                }
                temp2 = list.random().word
            }

            button3.text = arrayList[index].word
        }

    }
}