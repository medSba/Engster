package com.example.engster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Addpage extends AppCompatActivity {
    private EditText ex1,word;
    private Button btnimage;
    private FloatingActionButton ok;

    private RadioGroup radioType;
    private MyDataBase db;
    private static final int PICK_IMAGE=100;
    private ArrayList<byte[]> images = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Addpage");

        word= findViewById(R.id.word);
        ex1=findViewById(R.id.expression);
        radioType = findViewById(R.id.radioType);
        btnimage=findViewById(R.id.btn_upload);
        ok=findViewById(R.id.f);

        db=new MyDataBase(this);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioType.getCheckedRadioButtonId();
                String w = word.getText().toString();
                String ex = ex1.getText().toString();
                if (w.isEmpty()) {
                    Toast.makeText(Addpage.this, "Please enter a word or example", Toast.LENGTH_SHORT).show();
                } else if (ex.isEmpty()) {
                    Toast.makeText(Addpage.this, "Please enter an expression", Toast.LENGTH_SHORT).show();
                } else if (selectedId == R.id.radioword) {
                    // Perform insert for word
                    Notes n=new Notes(w,ex,"word");
                    long result = db.insertWordExpression(n); // Insert word and expression with type "word" into database
                    if (result > 0) {
                        int wordExpressionId = (int) result;
                        for (byte[] image : images) {
                            Images img=new Images(image,wordExpressionId);
                            db.insertImages(img);
                        }
                        Toast.makeText(Addpage.this, "Data inserted successfully as word", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Addpage.this, "Failed to insert data as word", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedId == R.id.radioexample) {
                    // Perform insert for example
                    Notes n=new Notes(w,ex,"example");
                    long result = db.insertWordExpression(n); // Insert word and expression with type "example" into database
                    if (result > 0) {
                        int wordExpressionId = (int) result;
                        for (byte[] image : images) {
                            Images img=new Images(image,wordExpressionId);
                            db.insertImages(img);
                        }
                        Toast.makeText(Addpage.this, "Data inserted successfully as example", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Addpage.this, "Failed to insert data as example", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Addpage.this, "Please select a type", Toast.LENGTH_SHORT).show();
                }
                Intent back = new Intent(Addpage.this, Home.class);
                startActivity(back);
            }
        });




        btnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    try {
                        InputStream is = getContentResolver().openInputStream(uri);
                        Bitmap stream = BitmapFactory.decodeStream(is);
                        byte[] image = IOUtils.toByteArray(is);
                        images.add(image);
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(this, "Selected " + count + " images", Toast.LENGTH_SHORT).show();
            } else if (data.getData() != null) {
                // Handle single image selection here
                Uri uri = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap stream = BitmapFactory.decodeStream(is);
                    byte[] image = IOUtils.toByteArray(is);
                    images.add(image);
                    is.close();
                    Toast.makeText(this, "Selected 1 image", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}