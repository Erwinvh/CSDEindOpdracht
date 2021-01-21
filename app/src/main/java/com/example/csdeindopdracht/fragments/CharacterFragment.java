package com.example.csdeindopdracht.fragments;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Relations.RunnerStatistics;
import com.example.csdeindopdracht.Logic.MainViewModel;
import com.example.csdeindopdracht.Logic.Repository;
import com.example.csdeindopdracht.R;

import java.util.Objects;


public class CharacterFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private MainViewModel mainViewModel;
    private Runner player;
    private Statistic statistic;

    private TextView name;
    private ProgressBar stamina;
    private ProgressBar speed;
    private ProgressBar topSpeed;
    private ImageButton englishFlag;
    private ImageButton dutchFlag;


    public CharacterFragment(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = getView().findViewById(R.id.title);
        stamina = getView().findViewById(R.id.stamina_bar);
        speed = getView().findViewById(R.id.speed_bar);
        topSpeed = getView().findViewById(R.id.top_speed_bar);
        englishFlag = getView().findViewById(R.id.english_flag);
        dutchFlag = getView().findViewById(R.id.dutch_flag);

        mainViewModel.getPlayer().observe(this, runnerStatistics -> {
            player = runnerStatistics.getRunner();
            statistic = runnerStatistics.getStatistic();

            name.setText(player.getName());
            stamina.setProgress((int) statistic.getRunDistance());
            speed.setProgress((int) statistic.getGeneralSpeed());
            topSpeed.setProgress((int) statistic.getTopSpeed());
        });

        englishFlag.setOnClickListener(v -> {
            // Select english flag
            LayerDrawable englishBorder = (LayerDrawable) ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.engels);
            GradientDrawable englishGradient = (GradientDrawable) Objects.requireNonNull(englishBorder).findDrawableByLayerId(R.id.english_flag_border);
            englishGradient.setStroke(R.dimen.flag_stroke_thickness, getResources().getColor(R.color.selected));

            // Deselect dutch flag
            LayerDrawable dutchBorder = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.nederlands);
            GradientDrawable dutchGradient = (GradientDrawable) Objects.requireNonNull(dutchBorder).findDrawableByLayerId(R.id.dutch_flag_border);
            dutchGradient.setStroke(R.dimen.flag_stroke_thickness, getResources().getColor(R.color.bright_blue));
        });

        dutchFlag.setOnClickListener(v -> {
            // Select Dutch flag
            LayerDrawable dutchBorder = (LayerDrawable) ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.nederlands);
            GradientDrawable dutchGradient = (GradientDrawable) Objects.requireNonNull(dutchBorder).findDrawableByLayerId(R.id.dutch_flag_border);
            dutchGradient.setStroke(R.dimen.flag_stroke_thickness, getResources().getColor(R.color.selected));

            // Deselect English flag
            LayerDrawable englishBorder = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.engels);
            GradientDrawable englishGradient = (GradientDrawable) Objects.requireNonNull(englishBorder).findDrawableByLayerId(R.id.english_flag_border);
            englishGradient.setStroke(R.dimen.flag_stroke_thickness, getResources().getColor(R.color.bright_blue));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false);
    }
}