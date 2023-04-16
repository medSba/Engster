package com.example.engster;


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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Imagepage extends AppCompatActivity {
    ImageView img;
    EditText answer;
    Button btn_submit;
    MyDataBase db=new MyDataBase(this);
    int currentQuestionIndex = 0;
    int id;
    @SuppressLint({"Range", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepage);

        getRandomQuestion();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Image Quiz");

        answer=findViewById(R.id.answ);
        btn_submit=findViewById(R.id.btnsubmit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer=answer.getText().toString();
                String correctExpression = getCorrectExpression();
                String correctWord = getCorrectWord();
                if (userAnswer.equalsIgnoreCase(correctExpression) || userAnswer.equalsIgnoreCase(correctWord)) {
                    // User's answer is correct
                    Toast.makeText(Imagepage.this, "Correct answer!", Toast.LENGTH_SHORT).show();
                } else {
                    // User's answer is incorrect
                    Toast.makeText(Imagepage.this, "InCorrect answer.", Toast.LENGTH_SHORT).show();
                }
                // Display next question or show results when all questions are answered
                currentQuestionIndex++;
                if (currentQuestionIndex <5) {
                    getRandomQuestion();
                } else {
                    showResults();
                }
                answer.setText("");
            }
        });



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
    private String getCorrectExpression() {
        String correctExpression = "";
        SQLiteDatabase db = new MyDataBase(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT expression FROM word_expression WHERE id = ?",new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            correctExpression = cursor.getString(cursor.getColumnIndex("expression"));

        }
        cursor.close();
        db.close();
        return correctExpression;
    }
    @SuppressLint("Range")
    private String getCorrectWord() {
        String correctword = "";
        SQLiteDatabase db = new MyDataBase(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT wordexample FROM word_expression WHERE id = ?",new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            correctword = cursor.getString(cursor.getColumnIndex("wordexample"));
        }
        cursor.close();
        db.close();
        return correctword;
    }

    @SuppressLint("Range")
    private void getRandomQuestion() {
        byte[] imageData;
        SQLiteDatabase db = new MyDataBase(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM image ORDER BY RANDOM() LIMIT 1", null);
        if (cursor.moveToFirst()) {
            imageData = cursor.getBlob(cursor.getColumnIndex("image_uri"));
            id=cursor.getInt(cursor.getColumnIndex("word_expression_id"));
            // Convert image data to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            if (img == null) {
                img = findViewById(R.id.imgq); // Initialize img if it is null
            }
            img.setImageBitmap(bitmap);
        }
        cursor.close();
        db.close();
    }

}
