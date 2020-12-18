package com.example.csdeindopdracht.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes the user settings.
 */
@Entity(tableName = "UserSetting")
public class UserSettings {


    @NonNull
    @PrimaryKey
    private String SettingID;

    @NonNull
    @ColumnInfo(name = "Difficulty")
    private int difficulty;

    @NonNull
    @ColumnInfo(name = "Language")
    private String Language;


    public int getDifficulty() {
        return difficulty;
    }

    @NonNull
    public String getLanguage() {
        return Language;
    }

    @NonNull
    public String getSettingID() {
        return SettingID;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setLanguage(@NonNull String language) {
        Language = language;
    }
}
