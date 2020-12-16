package com.example.csdeindopdracht.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.csdeindopdracht.R;
import com.example.csdeindopdracht.data.Runner;


public class CharacterFragment extends Fragment {

    public Runner player;


    public CharacterFragment() {
        // Required empty public constructor
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: remove testcode {
        player = new Runner();
        //TODO: }

        TextView name = getView().findViewById(R.id.NameField);
        name.setText(player.getPlayerName());

        ProgressBar stamina = getView().findViewById(R.id.StaminaBar);
        stamina.setProgress(player.getStamina());

        ProgressBar speed = getView().findViewById(R.id.SpeedBar);
        speed.setProgress(player.getSpeed());

        ProgressBar topSpeed = getView().findViewById(R.id.TopSpeedBar);
        topSpeed.setProgress(player.getMaxSpeed());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false);
    }
}