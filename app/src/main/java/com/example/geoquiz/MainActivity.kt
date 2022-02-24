package com.example.geoquiz

import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import android.os.Bundle
import android.view.Gravity.*
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.util.Log
import org.w3c.dom.Text

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var scoreTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_mideast, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )
    private var currentIndex = 0
    // score and answered tracking
    private var score = 0
    private var answered = Array<Boolean>(questionBank.size) { _ -> false }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_name)

        // Create View references
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        scoreTextView = findViewById(R.id.score_text_view)

        // Create buttons
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        nextButton.setOnClickListener { view : View ->
            updateQuestion(1)
        }
        prevButton.setOnClickListener { view : View ->
            updateQuestion(-1)
        }
        updateQuestion()
    }

    override fun onStart() {
        Log.d(TAG, "onStart() called")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume() called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause() called")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop() called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy() called")
        super.onDestroy()
    }

    private fun updateQuestion(indexChange: Int = 0) {
        currentIndex = (currentIndex + questionBank.size + indexChange) % questionBank.size
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId) // you must pass by resId
        scoreTextView.text = String.format("Score: %d/%d", score, questionBank.size)
    }

    fun createAnswerToast(
        messageResId: Int,
        xOffSet: Int = 0,
        yOffset: Int = 0
    ): Toast {
        val toast: Toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        toast.setGravity(TOP, xOffSet, yOffset)
        return toast
    }

    private fun checkAnswer(userAnswer: Boolean) {
        var messageResId = R.string.answered_toast
        if (!answered[currentIndex]) {
            if (questionBank[currentIndex].answer == userAnswer) {
                messageResId = R.string.correct_toast
                score += 1
            } else {
                messageResId = R.string.incorrect_toast
            }
            answered[currentIndex] = true
        }
        updateQuestion()
        return createAnswerToast(
            messageResId,
            yOffset = 600
        ).show()
    }
}