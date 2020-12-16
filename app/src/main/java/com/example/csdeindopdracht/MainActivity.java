package com.example.csdeindopdracht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csdeindopdracht.data.Storage;
import com.example.csdeindopdracht.fragments.BaseMapFragment;
import com.example.csdeindopdracht.fragments.CharacterFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences userPref;
    private static final String PREFS_NAME = "prefs";
    private static final String USER_DATA = "userData";
    public String currentFragment = "baseMap";
    public Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = userPref.getString("MyObject", "");
        if (json.length()==0){
            storage = new Storage("player");
        }else{
            storage = gson.fromJson(json, Storage.class);
        }

        this.userPref = getSharedPreferences(USER_DATA, MODE_PRIVATE);

        if (this.userPref.getBoolean("isFirstTime", true)){
            Fragment characterFragment = new CharacterFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,characterFragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new BaseMapFragment()).commit();
        }

        //TODO: construct buttons and listeners of the 3 buttons
        FloatingActionButton charbutton = findViewById(R.id.characterbutton);
        charbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment.equals("character")){
                    //TODO: return to basemap (with route?)

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,new BaseMapFragment()).commit();
                    currentFragment = "baseMap";

                    //TODO: set image back on button
                    return;
                }
                currentFragment = "character";
                //TODO: set button image to back
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,new CharacterFragment()).commit();

            }
        });

        FloatingActionButton Trainbutton = findViewById(R.id.Trainingbutton);
        Trainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment.equals("training")){
                    //TODO: return to basemap (with route?)
                    //TODO: set image back on button
                    return;
                }
                currentFragment = "training";
                //TODO: set button image to stop

            }
        });

        FloatingActionButton Racebutton = findViewById(R.id.RaceButton);
        Racebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment.equals("Race")){
                    //TODO: return to basemap (with route?)
                    //TODO: set image back on button
                    return;
                }
                currentFragment = "Race";
                //TODO: set button image to stop

            }
        });

    }
}