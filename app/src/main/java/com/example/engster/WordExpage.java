package com.example.engster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordExpage extends AppCompatActivity {
    private ArrayList<Notes> wordExpressions; // List of quiz questions
    private int currentQuestionIndex; // Index of the current quiz question
    private TextView questionTextView;
    private EditText answerEditText;
    private Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_expage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Word Quiz");
        // Initialize UI elements
        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitBtn = findViewById(R.id.submitBtn);

        // Set up click listener for submit button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        // Load quiz questions from your database
        MyDataBase myDataBase = new MyDataBase(this);
        wordExpressions = myDataBase.getAll();

        // Set the first quiz question
        currentQuestionIndex = 0;
        showQuestion();
    }
    // Show the current quiz question
    private void showQuestion() {
        if (currentQuestionIndex < wordExpressions.size()) {
            Notes notes = wordExpressions.get(currentQuestionIndex);
            questionTextView.setText(notes.getWordexample());
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quiz Completed")
                    .setMessage("Congratulations! You have completed the quiz.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Perform any desired action on OK button click, e.g. go back to main menu
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    // Check the user's answer and show the next quiz question
    private void checkAnswer() {
        if (currentQuestionIndex < wordExpressions.size()) {
            Notes notes = wordExpressions.get(currentQuestionIndex);
            String userAnswer = answerEditText.getText().toString();
            String correctAnswer = notes.getExpression().trim();
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                // User's answer is correct, move to the next question
                Toast.makeText(WordExpage.this,"Correct Answer",Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                answerEditText.setText(""); // Clear answer edit text
                showQuestion(); // Show the next question
            } else {
                // User's answer is incorrect, show a message or perform any other action
                // e.g. display a toast, show a dialog, etc.
                Toast.makeText(WordExpage.this, "Incorrect answer. Try again!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Quiz completed, show quiz completion message or redirect to another activity
            questionTextView.setText("Quiz completed!");
        }
    }
}