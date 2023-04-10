package com.example.engster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Editpage extends AppCompatActivity {
    private EditText upword,upexpression;
    private RadioGroup radiotype;
    private MyDataBase db;
    private FloatingActionButton edit;
    private int wordExpressionId;
    private ListView lsvimg;
    private RadioButton radword,radexa;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit");

        upword=findViewById(R.id.wupdate);
        upexpression=findViewById(R.id.exupdate);
        radiotype=findViewById(R.id.upradioType);
        radword=findViewById(R.id.upradioword);
        radexa=findViewById(R.id.upradioexample);
        edit=findViewById(R.id.update);

        db=new MyDataBase(this);
        // Get the word and expression data from the intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            wordExpressionId = bundle.getInt("id");
            String word = bundle.getString("wordexample");
            String expression = bundle.getString("expression");
            String type = bundle.getString("type");

            // Set the retrieved data to the corresponding views
            upword.setText(word);
            upexpression.setText(expression);
            radiotype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId==R.id.upradioword){
                        radiotype.check(checkedId);
                    } else if (checkedId==R.id.upradioexample) {
                        radiotype.check(checkedId);
                    }
                }
            });
        }

        // Handle update button click
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated word and expression data from the views
                String updatedWord = upword.getText().toString();
                String updatedExpression = upexpression.getText().toString();
                String updatedType = "";
                if (radword.isChecked()){
                    updatedType = "word";
                } else if (radexa.isChecked()) {
                    updatedType = "example";
                }


                // Update the word and expression data in the database
                Notes notes = new Notes();
                notes.setId(wordExpressionId);
                notes.setWordexample(updatedWord);
                notes.setExpression(updatedExpression);
                notes.setType(updatedType);
                db.updateWordExpression(notes);

                // Finish the activity
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this expression?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Delete the word expression from the database
                                db.deleteWordExpression(wordExpressionId);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                            }
                        }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}