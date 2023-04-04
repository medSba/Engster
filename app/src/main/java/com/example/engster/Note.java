package com.example.engster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Note extends AppCompatActivity {
    EditText Word,Expression;
    Button btnimage;
    FloatingActionButton ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Note");

        Word=findViewById(R.id.word);
        Expression=findViewById(R.id.expression);
        Word.getText().toString();
        Expression.getText().toString();
        ok=findViewById(R.id.f);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }
}