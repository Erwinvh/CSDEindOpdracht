package com.example.csdeindopdracht.Database.Entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a Race.
 */
@Entity(tableName = "Race")
public class Race {

    @NonNull
    @PrimaryKey
    private String RaceName;

    @NonNull
    @ColumnInfo(name = "isComplete")
    private boolean isComplete;

    @NonNull
    public String getRaceName() {
        return RaceName;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
