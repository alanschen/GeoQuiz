package com.example.geoquiz

import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import android.os.Bundle
import android.view.Gravity.*
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_name)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)


        var trueToast: Toast = Toast.makeText(
            this,
            R.string.correct_toast,
            Toast.LENGTH_SHORT
        )
        var falseToast: Toast = Toast.makeText(
            this,
            R.string.incorrect_toast,
            Toast.LENGTH_SHORT
        )
        // Setting gravity for the button to be on-top
        trueToast.setGravity(TOP, 0, 600)
        falseToast.setGravity(TOP, 0, 600)

        trueButton.setOnClickListener { view: View ->
            trueToast.show()
        }
        falseButton.setOnClickListener { view: View -> falseToast.show() }
    }
}