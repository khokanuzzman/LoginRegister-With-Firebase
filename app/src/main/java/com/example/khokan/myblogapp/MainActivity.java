package com.example.khokan.myblogapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mytoolbar= findViewById(R.id.mytoobar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("Photo Blog");

        mAuth= FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:
                logOut();
                return true;
            default:
                return false;

        }
    }

    private void logOut() {
        mAuth.signOut();
        sendToMain();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            sendToMain();
        }
    }

    private void sendToMain() {

        Intent intent =new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
