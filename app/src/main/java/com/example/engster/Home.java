package com.example.engster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends AppCompatActivity {
    DrawerLayout drLayout;
    NavigationView nvView;
    FloatingActionButton fab;
    ActionBarDrawerToggle abdt;//Acronyname of ActionBarDrawerToggle
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ListView listv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Note.class);
                startActivity(intent);
            }
        });

        drLayout=findViewById(R.id.homepage);
        nvView=findViewById(R.id.navView);

        abdt=new ActionBarDrawerToggle(this,drLayout,R.string.open,R.string.close);

        drLayout.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Log.i("Menu_TAG", "Home item clicked");
                        drLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.quiz:
                        Intent quizIntent = new Intent(Home.this, Quiz.class);
                        startActivity(quizIntent);
                        break;
                    case R.id.search:
                        Log.i("Menu_TAG", "Search item clicked");
                        drLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.night:
                        Log.i("Menu_TAG", "Mode Night item clicked");
                        drLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.support:
                        Log.i("Menu_TAG", "Support item clicked");
                        drLayout.closeDrawer(GravityCompat.START);
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
}