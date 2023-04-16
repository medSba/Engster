package com.example.engster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    DrawerLayout drLayout;
    NavigationView nvView;
    FloatingActionButton fab;
    ActionBarDrawerToggle abdt;//Acronyname of ActionBarDrawerToggle
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ListView listv;

    MyDataBase db=new MyDataBase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);


        listv=findViewById(R.id.listv);
        drLayout=findViewById(R.id.homepage);
        nvView=findViewById(R.id.navView);
        fab=findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this, Addpage.class);
                startActivity(intent);
            }
        });


        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notes select= (Notes) parent.getItemAtPosition(position);
                Intent intent=new Intent(Home.this,Editpage.class);
                intent.putExtra("id",select.getId());
                startActivity(intent);
            }
        });

        abdt=new ActionBarDrawerToggle(this,drLayout,R.string.open,R.string.close);

        drLayout.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home=new Intent(Home.this,Home.class);
                        startActivity(home);
                        break;

                    case R.id.quiz:
                        Intent quizIntent = new Intent(Home.this, Quiz.class);
                        startActivity(quizIntent);
                        break;
                    case R.id.search:
                        Intent search=new Intent(Home.this,Search.class);
                        startActivity(search);
                        break;

                    case R.id.night:
                        int currentNightMode = AppCompatDelegate.getDefaultNightMode();

                        int newNightMode = currentNightMode == AppCompatDelegate.MODE_NIGHT_YES ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;

                        AppCompatDelegate.setDefaultNightMode(newNightMode);

                        recreate();
                        break;

                    case R.id.support:
                        Intent sup=new Intent(Home.this,Support.class);
                        startActivity(sup);
                        break;
                    case R.id.logout:
                        gsc.signOut();
                        finish();
                        startActivity(new Intent(Home.this,SignIn.class));
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (abdt.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Notes> notes = db.getAll();
        noteAdapter na=new noteAdapter(this,R.layout.lsv,notes);
        listv.setAdapter(na);
    }

}