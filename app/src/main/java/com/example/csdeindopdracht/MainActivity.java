package com.example.csdeindopdracht;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
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
        TRAINING,
        RACE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //left button
        FloatingActionButton toCharacterButton = findViewById(R.id.button_character);
        toCharacterButton.setOnClickListener(v -> {
            if (!currentFragment.equals(Fragments.CHARACTER)) {
                //TODO: register previous fragment?
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new CharacterFragment())
                        .commit();
                currentFragment = Fragments.CHARACTER;
                toCharacterButton.setImageResource(R.drawable.stop_foreground);
                //TODO: change floatingbutton to globe
            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                toCharacterButton.setImageResource(R.drawable.ic_launcher_foreground);
            }
        });

        //Middle button
        FloatingActionButton trainingButton = findViewById(R.id.button_training);
        trainingButton.setOnClickListener(v -> {
            if (!currentFragment.equals(Fragments.TRAINING)) {
                //TODO: register previous fragment?
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new CharacterFragment()) // TODO change fragment
                        .commit();
                currentFragment = Fragments.TRAINING;
                trainingButton.setImageResource(R.drawable.stop_foreground);
            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                trainingButton.setImageResource(R.drawable.training_foreground);
            }
        });

        //right button
        FloatingActionButton racingButton = findViewById(R.id.button_race);
        racingButton.setOnClickListener(v -> {
            if (!currentFragment.equals(Fragments.RACE)){
                //TODO: register previous fragment?
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new CharacterFragment()) // TODO change fragment
                        .commit();
                currentFragment = Fragments.RACE;
                racingButton.setImageResource(R.drawable.stop_foreground);

            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                //TODO: find right icon to replace current one
                racingButton.setImageResource(R.drawable.race_foreground);
            }

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