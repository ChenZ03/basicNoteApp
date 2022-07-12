package com.example.noteappjava;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class NoteData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String title;
    private final String description;
    private final String timeStamp;

    public NoteData(String title, String description, String timeStamp) {
        this.title = title;
        this.description = description;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
