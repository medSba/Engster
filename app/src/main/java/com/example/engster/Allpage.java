package com.example.engster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Allpage extends AppCompatActivity {
    TextView questionTextView;
    Button submitButton;
    EditText answer;
    ImageView imgv;
    int id_image;

    int currentQuestionIndex = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpage);
        questionTextView = findViewById(R.id.txtvq);
        submitButton = findViewById(R.id.btnsub);
        answer=findViewById(R.id.ans);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Quiz");

        // Initialize UI with first question
        displayQuestion();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = answer.getText().toString();
                String correctAnswer = getCorrectAnswer();
                if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                    // User's answer is correct
                    Toast.makeText(Allpage.this, "Correct answer!", Toast.LENGTH_SHORT).show();
                } else {
                    // User's answer is incorrect
                    Toast.makeText(Allpage.this, "InCorrect answer.", Toast.LENGTH_SHORT).show();
                }

                // Display next question or show results when all questions are answered
                currentQuestionIndex++;
                if (currentQuestionIndex < getRandomQuestion().length()) {
                    displayQuestion();
                } else {
                    showResults();
                }
                answer.setText("");
            }
        });
    }

    private void displayQuestion() {
        String randomQuestion = getRandomQuestion();
        questionTextView.setText(randomQuestion);
        getRandomImage();
    }
    @SuppressLint("Range")
    private String getCorrectAnswer() {
        String correctAnswer = "";
        SQLiteDatabase db = new MyDataBase(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT expression FROM word_expression WHERE wordexample = ?", new String[]{getRandomQuestion()});
        if (cursor.moveToFirst()) {
            correctAnswer = cursor.getString(cursor.getColumnIndex("expression"));
        }
        cursor.close();
        db.close();
        return correctAnswer;
    }

    private void showResults() {
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
    @SuppressLint("Range")
    private String getRandomQuestion() {
        String randomQuestion = "";
        SQLiteDatabase db = new MyDataBase(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM word_expression ORDER BY RANDOM() LIMIT 1", null);
        if (cursor.moveToFirst()) {
            randomQuestion = cursor.getString(cursor.getColumnIndex("wordexample"));
            id_image = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        }
        cursor.close();
        db.close();
        return randomQuestion;
    }
    @SuppressLint("Range")
    private void getRandomImage() {
        byte[] imageData;
        SQLiteDatabase db = new MyDataBase(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM image WHERE word_expression_id=?", new String[]{String.valueOf(id_image)});
        if (cursor.moveToFirst()) {
            imageData = cursor.getBlob(cursor.getColumnIndex("image_uri"));
            // Convert image data to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            if (imgv == null) {
                imgv = findViewById(R.id.imageall); // Initialize img if it is null
            }
            imgv.setImageBitmap(bitmap);
        }
        cursor.close();
        db.close();
    }

}