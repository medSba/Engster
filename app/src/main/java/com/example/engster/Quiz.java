package com.example.engster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton all,wex,img;
    Button ok;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quiz");


       radioGroup=findViewById(R.id.radgrp);
       all=findViewById(R.id.radall);
       wex=findViewById(R.id.radwa);
       img=findViewById(R.id.radimg);
       ok=findViewById(R.id.go);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (all.isChecked()){
                    ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent all=new Intent(Quiz.this,Allpage.class);
                        startActivity(all);
                    }
                });

                }
                else if (wex.isChecked()) {
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                    Intent wex=new Intent(Quiz.this,WordExpage.class);
                    startActivity(wex);
                        }
                    });
                }
                else if (img.isChecked()) {
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                    Intent img=new Intent(Quiz.this,Imagepage.class);
                    startActivity(img);
                }
            });
                }
            }
        });


    }


}