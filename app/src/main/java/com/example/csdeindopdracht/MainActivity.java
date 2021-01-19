package com.example.csdeindopdracht;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.csdeindopdracht.fragments.BaseMapFragment;
import com.example.csdeindopdracht.fragments.CharacterFragment;
import com.example.csdeindopdracht.fragments.raceFragment;
import com.example.csdeindopdracht.fragments.trainingFragment;
import com.example.csdeindopdracht.ors.DirectionsPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // Current and all fragments.
    private Fragments currentFragment = Fragments.BASE_MAP;
    private FloatingActionButton toCharacterButton;
    private FloatingActionButton trainingButton;
    private  FloatingActionButton racingButton;


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

        DirectionsPost.executeExample(getApplicationContext());

        //left button
        toCharacterButton = findViewById(R.id.button_character);
        toCharacterButton.setOnClickListener(v -> {
            if (!currentFragment.equals(Fragments.CHARACTER)) {
                //TODO: register previous fragment?
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new CharacterFragment())
                        .commit();
                currentFragment = Fragments.CHARACTER;
                resetButtons();
                toCharacterButton.setImageResource(R.drawable.stop_foreground);
                //TODO: change floatingbutton to globe
            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                resetButtons();
            }
        });

        //Middle button
        trainingButton = findViewById(R.id.button_training);
        trainingButton.setOnClickListener(v -> {
            if (!currentFragment.equals(Fragments.TRAINING)) {
                //TODO: register previous fragment?
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new trainingFragment()) // TODO change fragment
                        .commit();
                currentFragment = Fragments.TRAINING;
                resetButtons();
                trainingButton.setImageResource(R.drawable.stop_foreground);
            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;
                resetButtons();
            }
        });

        //right button
        racingButton = findViewById(R.id.button_race);
        racingButton.setOnClickListener(v -> {
            if (!currentFragment.equals(Fragments.RACE)){
                //TODO: register previous fragment?
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new raceFragment()) // TODO change fragment
                        .commit();
                currentFragment = Fragments.RACE;
                resetButtons();
                racingButton.setImageResource(R.drawable.stop_foreground);

            }else{
                //TODO: turn back to previous fragment or map fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentcontainer, new BaseMapFragment())
                        .commit();
                currentFragment = Fragments.BASE_MAP;

                resetButtons();
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


    public void resetButtons(){
        //TODO: find right icon to replace current one
        racingButton.setImageResource(R.drawable.race_foreground);
        trainingButton.setImageResource(R.drawable.training_foreground);
        toCharacterButton.setImageResource(R.drawable.ic_launcher_foreground);
    }
}