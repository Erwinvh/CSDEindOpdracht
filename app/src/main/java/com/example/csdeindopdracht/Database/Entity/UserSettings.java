package com.example.csdeindopdracht.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes the user settings.
 */
@Entity(tableName = "UserSetting", indices = {@Index("settingID"), @Index(value = {"Difficulty", "Language"})})
public class UserSettings {

    @NonNull
    @PrimaryKey
    private final String settingID;

    @ColumnInfo(name = "Difficulty")
    private int difficulty;

    @NonNull
    @ColumnInfo(name = "Language")
    private String language;

    public UserSettings(@NonNull String settingID, int difficulty, @NonNull String language) {
        this.settingID = settingID;
        this.difficulty = difficulty;
        this.language = language;
    }

    @NonNull
    public String getSettingID() {
        return settingID;
    }

    public int getDifficulty() {
        return difficulty;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setLanguage(@NonNull String language) {
        this.language = language;
    }
}
