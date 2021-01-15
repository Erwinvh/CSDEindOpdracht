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
                //TODO: change floatingbutton to globe
            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                //TODO: return icon back to the original icon
            }
        });

        //Middle button
        FloatingActionButton trainingButton = findViewById(R.id.button_training);
        trainingButton.setOnClickListener(v -> {
            if (!currentFragment.equals(Fragments.TRAINING)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new CharacterFragment()) // TODO change fragment
                        .commit();
                currentFragment = Fragments.TRAINING;
            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                //TODO: return icon back to the original icon
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
                //TODO: change floatingbutton to stop
            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                //TODO: return icon back to the original icon
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