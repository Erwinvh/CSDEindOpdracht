package com.example.csdeindopdracht;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.csdeindopdracht.Database.Database;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database database = Database.getINSTANCE(this);
    }
}