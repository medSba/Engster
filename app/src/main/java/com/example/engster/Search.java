package com.example.engster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    MyDataBase db;
    private EditText editText;
    private Button btn_s;
    private RecyclerView rv;
    private SearchAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        editText=findViewById(R.id.edit_search);
        btn_s=findViewById(R.id.btn_search);
        rv=findViewById(R.id.SearchResult);

        db = new MyDataBase(this); // Initialize your database instance

        // Set up RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        sa = new SearchAdapter(); // Initialize your RecyclerView adapter
        rv.setAdapter(sa);

        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editText.getText().toString();
                ArrayList<Notes> searchResults = search(keyword);
                sa.setSearchResults(searchResults);
                sa.notifyDataSetChanged();
                if (searchResults.isEmpty()) {
                    Toast.makeText(Search.this, "No results found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("Range")
    public ArrayList<Notes> search(String keyword) {
        ArrayList<Notes> searchResults = new ArrayList<>();
        SQLiteDatabase mdb = db.getReadableDatabase();

        // Define the search query
        String selectQuery = "SELECT * FROM word_expression WHERE wordexample LIKE '%"
                + keyword + "%' OR expression LIKE '%" + keyword + "%'OR type LIKE '%" + keyword + "%'";

        Cursor cursor = mdb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(cursor.getInt(cursor.getColumnIndex("id")));
                notes.setWordexample(cursor.getString(cursor.getColumnIndex("wordexample")));
                notes.setExpression(cursor.getString(cursor.getColumnIndex("expression")));
                notes.setType(cursor.getString(cursor.getColumnIndex("type")));
                searchResults.add(notes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        mdb.close();
        return searchResults;
    }

}