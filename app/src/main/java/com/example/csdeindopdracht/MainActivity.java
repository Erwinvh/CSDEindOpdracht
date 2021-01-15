package com.example.csdeindopdracht;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.csdeindopdracht.fragments.BaseMapFragment;
import com.example.csdeindopdracht.fragments.CharacterFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // Current and all fragments.
    private Fragments currentFragment = Fragments.BASE_MAP;
    private enum Fragments {
        BASE_MAP,
        CHARACTER,
        TRAINING
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton toCharacterButton = findViewById(R.id.button_character);
        toCharacterButton.setOnClickListener(v -> {
            if (currentFragment.equals(Fragments.BASE_MAP)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new CharacterFragment())
                        .commit();
                currentFragment = Fragments.CHARACTER;
            }
        });

        FloatingActionButton trainingButton = findViewById(R.id.button_training);
        trainingButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentcontainer, new CharacterFragment()) // TODO change fragment
                    .commit();
            currentFragment = Fragments.CHARACTER;
        });

        FloatingActionButton racingButton = findViewById(R.id.button_race);
        racingButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentcontainer, new CharacterFragment()) // TODO change fragment
                    .commit();
            currentFragment = Fragments.CHARACTER;
        });
    }

    @Override
    public void onBackPressed() {
        if (!currentFragment.equals(Fragments.BASE_MAP)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentcontainer, new BaseMapFragment())
                    .commit();
            currentFragment = Fragments.BASE_MAP;
        } else finish();
    }
}