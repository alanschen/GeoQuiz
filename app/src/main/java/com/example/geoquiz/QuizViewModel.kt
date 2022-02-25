package com.example.geoquiz
import androidx.lifecycle.ViewModel
import android.util.Log

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_mideast, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )
    val bankSize = questionBank.size
    var currentIndex = 0
    var score = 0

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

    val curQuestion: Question
        get() = questionBank[currentIndex]

    fun getQuestionBank(): List<Question> {
        return questionBank
    }

    fun updateIndex(offset: Int = 0) {
        currentIndex = (currentIndex + bankSize + offset) % bankSize
    }
}