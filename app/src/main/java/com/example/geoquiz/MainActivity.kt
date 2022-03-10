package com.example.geoquiz
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val CUR_INDEX_KEY = "curIndex"
private const val CUR_SCORE_KEY = "curScore"
private const val CUR_ANSWERED_KEY = "questionBank"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var scoreTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private val questionBank: List<Question> by lazy {
        quizViewModel.getQuestionBank()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        // Set ViewModel: quizViewModel
        val provider : ViewModelProvider = ViewModelProvider(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewById: $quizViewModel")
        // Update QuizModelView
        val currentIndex : Int = savedInstanceState?.getInt(CUR_INDEX_KEY, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        val score : Int = savedInstanceState?.getInt(CUR_SCORE_KEY, 0) ?: 0
        quizViewModel.score = score
        val answeredList : BooleanArray = savedInstanceState?.getBooleanArray(
            CUR_ANSWERED_KEY
        ) ?: BooleanArray(questionBank.size)
        quizViewModel.answeredList = answeredList
        Log.d(TAG, "Restored score: %d, index: %d".format(score, currentIndex))

        // Create component View references
        setTitle(R.string.app_name)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        scoreTextView = findViewById(R.id.score_text_view)

        // Create buttons
        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.curQuestion.answer
            val cheatIntent = CheatActivity.newIntent(
                this@MainActivity,
                answerIsTrue
            )
            startActivity(cheatIntent)
        }

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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.d(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(CUR_INDEX_KEY, quizViewModel.currentIndex)
        savedInstanceState.putInt(CUR_SCORE_KEY, quizViewModel.score)
        savedInstanceState.putBooleanArray(CUR_ANSWERED_KEY, quizViewModel.answeredList)
        Log.d(TAG, "Saved score: %d, index: %d".format(
            quizViewModel.score,
            quizViewModel.currentIndex)
        )
    }

    override fun onStop() {
        Log.d(TAG, "onStop() called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy() called")
        super.onDestroy()
    }

    private fun updateQuestion(offset: Int = 0) {
        quizViewModel.updateIndex(offset)
        questionTextView.setText(quizViewModel.curQuestion.textResId)
        scoreTextView.text = String.format(
            "Score: %d/%d",
            quizViewModel.score,
            quizViewModel.bankSize
        )
    }

    private fun checkAnswer(userAnswer: Boolean) {
        var messageResId = R.string.answered_toast
        val curQuestion: Question = quizViewModel.curQuestion
        if (!quizViewModel.curQuestionAnswered) {
            if (curQuestion.answer == userAnswer) {
                messageResId = R.string.correct_toast
                quizViewModel.score += 1
            } else {
                messageResId = R.string.incorrect_toast
            }
            quizViewModel.markAnswered()
        }
        updateQuestion()
        return createAnswerToast(
            messageResId,
        ).show()
    }

    private fun createAnswerToast(
        messageResId: Int,
        xOffSet: Int = 0,
        yOffset: Int = 0
    ): Toast {
        val toast: Toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        return toast
    }
}